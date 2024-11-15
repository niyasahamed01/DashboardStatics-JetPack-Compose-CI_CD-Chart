package com.example.staticsapp.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.staticsapp.model.Slice
import kotlinx.coroutines.delay

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

        Header(slices)
        CustomChart(slices)
    }
}











