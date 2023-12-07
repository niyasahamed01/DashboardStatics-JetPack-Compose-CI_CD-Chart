package com.example.staticsapp.screen

import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.staticsapp.model.Slice


@Composable
fun Header(slices: List<Slice>) {

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Text(
            fontSize = 20.sp,
            text = if (slices.size > 4) "Job Stats" else "Invoice Stats",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(8.dp)
        )
    }

    Divider(thickness = 1.dp, color = Color.Black)

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
                    text = if (slices.size > 4) "60 Jobs" else "Total value ($50,000)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(3.dp)
                )

                Text(
                    fontSize = 15.sp,
                    text = if (slices.size > 4) "25 of 60 completed" else "$15,000 collected",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(3.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(15.dp)

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
}
