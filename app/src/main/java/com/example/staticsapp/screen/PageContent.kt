package com.example.staticsapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.staticsapp.model.Slice

@Composable
fun PageContent(navController: NavHostController) {

    val pinkColor = Color(0xFFFF69B4)
    val skyBlueColor = Color(0xFF87CEEB)
    val oceanBlueColor = Color(0xFF0077BE)
    val orangeColor = Color(0xFFFFA500)
    val greenColor = Color(0xFF719671)

    val slices = listOf(
        Slice(value = 65f, color = pinkColor),
        Slice(value = 40f, color = skyBlueColor),
        Slice(value = 30f, color = oceanBlueColor),
        Slice(value = 25f, color = orangeColor),
        Slice(value = 15f, color = greenColor),
    )
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        Profile()
        CustomPieChart(slices)
        JobsChart(navController)
        InvoiceChart(navController)
    }

}