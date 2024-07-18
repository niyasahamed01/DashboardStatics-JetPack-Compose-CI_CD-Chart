package com.example.staticsapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.staticsapp.model.InvoiceApiModel
import com.example.staticsapp.model.InvoiceStatus
import com.example.staticsapp.model.JobApiModel
import com.example.staticsapp.model.JobStatus
import com.example.staticsapp.model.Slice
import com.example.staticsapp.remote.DataRepository
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf


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
                                fontSize = 15.sp,
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
                                fontSize = 15.sp,
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



