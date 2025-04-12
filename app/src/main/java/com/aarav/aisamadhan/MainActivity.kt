package com.aarav.aisamadhan

import ImageViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
// For XML

import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import com.aarav.aisamadhan.ui.theme.GeminiAPITheme
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.aarav.aisamadhan.navigation.NavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = ViewModelProvider(this)[ImageViewModel::class.java]

        setContent {
            GeminiAPITheme {
//                HuggingFaceImage(viewModel = viewModel)
//                OnBoardingScreen()
//                HomeScreen()
                val navController = rememberNavController()
                NavGraph(navController, viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GeminiAPITheme {
        Greeting("Android")
    }
}

@Composable
fun HuggingFaceImage(viewModel : ImageViewModel) {
    val bitmap by viewModel.bitmap.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.generateImage("Create a sleek and professional logo for a fintech company called 'FinEdge'. Use shades of navy blue and grey, and include a sharp, geometric symbol that represents finance and trust.")
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "Generated Image",
            modifier = Modifier.fillMaxSize()
        )
    } ?: Text("Generating image...")
}

