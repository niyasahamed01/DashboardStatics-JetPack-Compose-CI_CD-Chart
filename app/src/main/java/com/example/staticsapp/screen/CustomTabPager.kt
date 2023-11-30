package com.example.staticsapp.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import com.example.staticsapp.model.InvoiceApiModel
import com.example.staticsapp.model.InvoiceStatus
import com.example.staticsapp.model.JobApiModel
import com.example.staticsapp.model.JobStatus
import com.example.staticsapp.model.Slice
import com.example.staticsapp.remote.DataRepository
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@Composable
fun StatusTabs(dataRepository: DataRepository, slices: List<Slice>) {

    val jobStatusValues = JobStatus.values().toList()
    val invoiceStatusValues = InvoiceStatus.values().toList()
    var tabIndex by remember { mutableStateOf(0) }

    val jobsFlow by remember(tabIndex) {
        derivedStateOf {
            when (tabIndex) {
                in jobStatusValues.indices -> dataRepository.observeJobsByStatus(jobStatusValues[tabIndex])
                else -> flowOf(emptyList())
            }
        }
    }

    val invoiceFlow by remember(tabIndex) {
        derivedStateOf {
            when (tabIndex) {
                in invoiceStatusValues.indices -> dataRepository.observeInvoiceByStatus(
                    invoiceStatusValues[tabIndex]
                )

                else -> flowOf(emptyList())
            }
        }
    }


    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            edgePadding = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (slices.size > 4) {

                jobStatusValues.forEachIndexed { index, jobStatus ->

                    val count = when (jobStatus) {
                        JobStatus.YetToStart -> 10
                        JobStatus.InProgress -> 15
                        JobStatus.Canceled -> 5
                        JobStatus.Completed -> 25
                        JobStatus.Incomplete -> 5
                    }
                    Tab(
                        text = {
                            Text(
                                text = "${jobStatus.name} (${count})",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Start
                            )
                        },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                    )
                }

            } else {

                invoiceStatusValues.forEachIndexed { index, invoiceStatus ->

                    val count = when (invoiceStatus) {

                        InvoiceStatus.Draft -> "$10000"
                        InvoiceStatus.Pending -> "$20000"
                        InvoiceStatus.Paid -> "$1500000"
                        InvoiceStatus.BadDebt -> "$5000"

                    }
                    Tab(
                        text = {
                            Text(
                                text = "${invoiceStatus.name} (${count})",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Start
                            )
                        },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        val jobList by jobsFlow.collectAsState(emptyList())
        val invoiceList by invoiceFlow.collectAsState(emptyList())

        if (slices.size > 4) {
            JobList(jobList = jobList)
        } else {
            InVoiceList(invoiceList = invoiceList)
        }
    }
}

@Composable
fun InVoiceList(invoiceList: List<InvoiceApiModel>) {
    LazyColumn {
        items(invoiceList) { invoice ->
            JobCard(invoice = invoice)
        }
    }
}

@Composable
fun JobList(jobList: List<JobApiModel>) {

    SwipeRefreshCompose(jobList)

}

@Composable
fun JobCard(job: JobApiModel? = null, invoice: InvoiceApiModel? = null) {

    val localStartTime = job?.startTime?.let { convertGmtToLocal(it) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(0.8.dp, Color.Black)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {

                if (job != null) {
                    Text(
                        text = "#${job.jobNumber.toString()}",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(3.dp),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = job.title,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(3.dp),
                        color = Color.Black
                    )

                    Text(
                        text = localStartTime.toString(),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(3.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Text(
                        text = "#${invoice?.invoiceNumber.toString()}",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(3.dp),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    invoice?.customerName?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(3.dp),
                            color = Color.Black
                        )
                    }
                    invoice?.total?.let {
                        Text(
                            text = "$${it.toString()}",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(3.dp),
                            color = Color.Black
                        )
                    }
                }

            }
        }
    }
}


@Composable
fun SwipeRefreshCompose(invoiceList: List<JobApiModel>) {

    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(3000)
            refreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true },
    ) {
        LazyColumn {
            items(invoiceList) { job ->
                JobCard(job = job)
            }
        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun convertGmtToLocal(startTime: String): String {

    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Assuming the input is in UTC

        val outputFormat = SimpleDateFormat("dd/MMM/yyyy hh:mm a", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault() // Use the device's default time zone
        val date = inputFormat.parse(startTime)
        outputFormat.format(date)
    } catch (e: Exception) {
        startTime
    }
}

