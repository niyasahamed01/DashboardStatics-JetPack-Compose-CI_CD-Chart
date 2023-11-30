package com.example.staticsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.staticsapp.ui.theme.StaticsAppTheme
import com.example.staticsapp.model.JobApiModel
import com.example.staticsapp.model.JobStatus
import com.example.staticsapp.model.Slice
import com.example.staticsapp.screen.DetailScreen
import com.example.staticsapp.screen.InvoiceChart
import com.example.staticsapp.screen.JobsChart

class MainActivity : ComponentActivity() {

    val pinkColor = Color(0xFFFF69B4)
    val skyBlueColor = Color(0xFF87CEEB)
    val oceanBlueColor = Color(0xFF0077BE)
    val orangeColor = Color(0xFFFFA500)
    val greenColor = Color(0xFF719671) // Custom Green color

    val slices = listOf(
        Slice(value = 65f, color = pinkColor, text = "Yet to Start(10)"),
        Slice(value = 40f, color = skyBlueColor, text = "In-Progress(15)"),
        Slice(value = 30f, color = oceanBlueColor, text = "Cancelled(5)"),
        Slice(value = 25f, color = orangeColor, text = "Completed(25)"),
        Slice(value = 15f, color = greenColor, text = "In-Complete(5)"),
    )

    val invoice = listOf(
        Slice(value = 40f, color = skyBlueColor, text = "Draft($10000)"),
        Slice(value = 30f, color = oceanBlueColor, text = "Pending($20000)"),
        Slice(value = 25f, color = orangeColor, text = "Paid($150000)"),
        Slice(value = 15f, color = greenColor, text = "Bad Debit($5000)"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaticsAppTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "jobList") {
                        composable("jobList") {
                            Dashboard(navController)
                        }
                        composable("jobDetail") {
                            DetailScreen(slices, navController)
                        }
                        composable("invoiceDetail") {
                            DetailScreen(invoice, navController)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Dashboard(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)

        ) {
            item {
                Text(
                    fontSize = 22.sp,
                    text = "DashBoard",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(0.8.dp, Color.Black)  // Border around the card
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            ) {

                                Text(
                                    fontSize = 20.sp,
                                    text = "Hello, Henry Jones!",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Text(
                                    fontSize = 15.sp,
                                    text = "Friday, January 6th 2022",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            Image(
                                painter = painterResource(id = R.drawable.employee),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .align(Alignment.CenterVertically)

                            )
                        }
                    }
                }
            }
            item {
                JobsChart(navController)
            }

            item {
                InvoiceChart(navController)
            }


        }
    }

}


@Composable
fun JobList(jobs: List<JobApiModel>) {
    LazyColumn {
        items(jobs) { job ->
            JobListItem(job = job)
        }
    }
}

@Composable
fun JobListItem(job: JobApiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Colored status indicator
        val statusColor = when (job.status) {
            JobStatus.YetToStart -> Color.Gray
            JobStatus.InProgress -> Color.Blue
            JobStatus.Canceled -> Color.Red
            JobStatus.Completed -> Color.Green
            JobStatus.Incomplete -> Color.Yellow
        }

        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(statusColor)
        )

        // Spacer for separation
        Spacer(modifier = Modifier.width(8.dp))

        // Job title
        Text(
            text = job.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()

    StaticsAppTheme(darkTheme = false) {
        Dashboard(navController = navController )
    }
}