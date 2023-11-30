package com.example.staticsapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.staticsapp.model.Slice

@Composable
fun CustomChart(slices: List<Slice>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {

        slices.forEach { slice ->

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(25.dp) // Set the desired size for the square
                        .background(slice.color)
                        .weight(0.1f) // Set your desired background color here
                )
                Text(
                    text = slice.text,
                    color = Color.Black,
                    modifier = Modifier.weight(0.8f)
                )
            }

        }
    }
}