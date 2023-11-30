package com.example.staticsapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.staticsapp.R
import com.example.staticsapp.model.Slice
import com.example.staticsapp.remote.DataRepository
import com.example.staticsapp.ui.theme.StaticsAppTheme
import zuper.dev.android.dashboard.data.remote.ApiDataSource


@Composable
fun DetailScreen(slices: List<Slice>, navController: NavHostController) {

    Surface(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column {

            Row(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            navController.navigateUp()
                        }
                        .clip(MaterialTheme.shapes.medium)

                )
                Text(
                    fontSize = 20.sp,
                    text = if (slices.size > 4) "Jobs(60)" else "Invoice Stats($50,000)",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }


            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Text(
                    fontSize = 15.sp,
                    text = if (slices.size > 4) "60 Jobs" else "Total value (\$50,000)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(5.dp)
                )

                Text(
                    fontSize = 15.sp,
                    text = if (slices.size > 4) "25 of 60 completed" else "$15,000 collected",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(5.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(30.dp)

            ) {


                slices.forEachIndexed { index, slice ->
                    val cornerRadius = when (index) {
                        0 -> RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 0.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 0.dp
                        )

                        slices.size - 1 -> RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 8.dp
                        )

                        else -> RoundedCornerShape(0.dp)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(slice.value)
                            .clip(cornerRadius),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(slice.color),
                        )

                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
            ) {
                StatusTabs(dataRepository = DataRepository(apiDataSource = ApiDataSource()), slices)
            }
        }
    }
    }







