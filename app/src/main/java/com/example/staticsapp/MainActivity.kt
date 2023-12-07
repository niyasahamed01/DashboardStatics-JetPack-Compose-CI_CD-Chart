package com.example.staticsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.staticsapp.screen.Dashboard
import com.example.staticsapp.screen.DetailScreen
import com.example.staticsapp.screen.PageContent
import com.example.staticsapp.ui.theme.StaticsAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaticsAppTheme(darkTheme = false) {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {

    var showSplashScreen by remember { mutableStateOf(true) }

    if (showSplashScreen) {
        SplashScreen(onSplashScreenDismiss = { showSplashScreen = false })

    } else {

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "dashboard") {
                composable("dashboard") {
                    PageContent(navController)
                }
                composable("detailScreen/{type}") { backStackEntry ->
                    val type = backStackEntry.arguments?.getString("type") ?: ""
                    DetailScreen(type, navController)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()

    StaticsAppTheme(darkTheme = false) {
        Dashboard(navController = navController)
    }
}