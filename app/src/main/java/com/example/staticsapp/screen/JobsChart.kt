package com.example.staticsapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.example.staticsapp.model.Slice

@Composable
fun JobsChart(navController: NavHostController) {

    val pinkColor = Color(0xFFFF69B4)
    val skyBlueColor = Color(0xFF87CEEB)
    val oceanBlueColor = Color(0xFF0077BE)
    val orangeColor = Color(0xFFFFA500)
    val greenColor = Color(0xFF719671)


    val slices = listOf(
        Slice(value = 65f, color = pinkColor, text = "Yet to Start(10)"),
        Slice(value = 40f, color = skyBlueColor, text = "In-Progress(15)"),
        Slice(value = 30f, color = oceanBlueColor, text = "Cancelled(5)"),
        Slice(value = 25f, color = orangeColor, text = "Completed(25)"),
        Slice(value = 15f, color = greenColor, text = "In-Complete(5)"),
    )

    var state by remember { mutableStateOf(0f) }


    LaunchedEffect(key1 = Unit, block = {
        delay(1000L)
        state = 1f
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(0.8.dp, Color.Black)
                .clickable {
                    navController.navigate("detailScreen/type1")
                }// Border around the card
        ) {

            Header(slices = slices)
            CustomChart(slices = slices)

        }

        InvoiceChart(navController)

    }


}



