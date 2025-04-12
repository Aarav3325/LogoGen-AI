package com.aarav.aisamadhan.screens

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.decode.ImageDecoderDecoder
import coil.decode.GifDecoder
import coil.ImageLoader
import coil.compose.AsyncImage
import com.aarav.aisamadhan.ui.theme.poppins

@Composable
fun OnBoardingScreen(navigateToHome : () -> Unit){

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        val (logo, title, des, btn, name) = createRefs()

        SoftGradientBackground(modifier = Modifier.constrainAs(logo) {
            top.linkTo(parent.top, margin = 150.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        Text(
            text = "LogoGen AI",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppins,
            textAlign = TextAlign.Justify,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(logo.bottom, margin = 0.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "Create AI Logos Instantly",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppins,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(name.bottom, margin = 18.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "Designing a logo has never been this easy.\n" +
                    "Just enter your brand name and ideas,\n" +
                    "and let our AI craft a unique identity for you.\n" +
                    "Fast, creative, and completely hassle-free.",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.constrainAs(des) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(title.bottom, margin = 6.dp)
            }
        )

        FilledTonalButton(modifier = Modifier.padding(start = 16.dp, end = 16.dp).height(55.dp).fillMaxWidth().constrainAs(btn) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, margin = 68.dp)
        },
            colors = ButtonDefaults.filledTonalButtonColors(Color(0xFF4756DF)),
            onClick = navigateToHome
        ){
            Row(modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
                ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppins,
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Composable
fun SoftGradientBackground(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    // Create an ImageLoader with GIF support
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Box(
        modifier = modifier
//            .background(
//                Brush.radialGradient(
//                    colors = listOf(
//                        Color(0xFFE1ECF7), // soft bluish
//                        Color(0xFFF2E9F9), // soft purplish
//                        Color(0xFFFFFFFF)  // white fallback
//                    ),
//                    center = Offset(500f, 500f),
//                    radius = 1000f
//                )
//            )
    ) {
        // Your content here, e.g., logo, GIF, etc.
        AsyncImage(
            model = "https://cdn.dribbble.com/userupload/22039375/file/original-13c329c2ab2654bf2caf73c92710b82e.gif",
            imageLoader = imageLoader,
            contentDescription = null,
            modifier = Modifier.size(width = 500.dp, height = 300.dp)
        )
    }
}
