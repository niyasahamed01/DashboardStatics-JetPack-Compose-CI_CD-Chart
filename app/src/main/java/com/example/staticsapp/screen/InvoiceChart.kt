package com.example.staticsapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.example.staticsapp.model.Slice

@Composable
fun InvoiceChart(navController: NavHostController) {

    val skyBlueColor = Color(0xFF87CEEB)
    val oceanBlueColor = Color(0xFF0077BE)
    val orangeColor = Color(0xFFFFA500)
    val greenColor = Color(0xFF719671) // Custom Green color


    val slices = listOf(
        Slice(value = 40f, color = skyBlueColor, text = "Draft($10000)"),
        Slice(value = 30f, color = oceanBlueColor, text = "Pending($20000)"),
        Slice(value = 25f, color = orangeColor, text = "Paid($150000)"),
        Slice(value = 15f, color = greenColor, text = "Bad Debit($5000)"),
    )

    var state by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = Unit, block = {
        delay(1000L)
        state = 1f
    })

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(0.8.dp, Color.Black)
            .clickable {
                navController.navigate("detailScreen/type2")
            } // Border around the card
    ) {
        Box(
            modifier = Modifier
                .background(Color.White).fillMaxWidth()
        ) {
            Text(
                fontSize = 20.sp,
                text = "Invoice Stats",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(8.dp)
            )

        }
        Divider(
            modifier = Modifier
                .height(1.dp) // Set the desired height for the divider
                .fillMaxWidth()
                .background(Color.Black) // Set your desired color for the divider
        )
        Box(
            modifier = Modifier
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(3.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    Text(
                        fontSize = 15.sp,
                        text = "Total value ($50,000)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(3.dp)
                    )

                    Text(
                        fontSize = 15.sp,
                        text = "$15,000 collected",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(3.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(25.dp)

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
            }
        }

        CustomChart(slices)
    }
}











