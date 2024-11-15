package com.example.staticsapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.staticsapp.model.InvoiceApiModel
import com.example.staticsapp.model.JobApiModel
import com.example.staticsapp.remote.SampleData

@Composable
fun JobCard(job: JobApiModel? = null, invoice: InvoiceApiModel? = null) {

    val localStartTime = job?.startTime?.let { SampleData.convertGmtToLocal(it) }

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