package com.aarav.aisamadhan.screens

import ImageViewModel
import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.aarav.aisamadhan.R
import com.aarav.aisamadhan.ui.theme.poppins

@Composable
fun HomeScreen(navigateToResult: () -> Unit, viewModel: ImageViewModel){

    var gen by remember {
        mutableStateOf(false)
    }

    var brandName by remember {
        mutableStateOf("")
    }

    var tagline by remember {
        mutableStateOf("")
    }

    var sector by remember {
        mutableStateOf("")
    }

    var brandValues by remember {
        mutableStateOf("")
    }

    var style by remember {
        mutableStateOf("")
    }

    var colors by remember {
        mutableStateOf("")
    }

    val prompt = buildString {
        append("Create a logo for a brand named \"$brandName\".")

        if (tagline.isNotBlank()) {
            append(" The tagline is \"$tagline\".")
        }

        if (sector.isNotBlank()) {
            append(" The brand belongs to the \"$sector\" sector.")
        }

        if (brandValues.isNotBlank()) {
            append(" It should reflect the values: $brandValues.")
        }

        if (style.isNotBlank()) {
            append(" Preferred design style: $style.")
        }

        if (colors.isNotBlank()) {
            append(" Use these colors: $colors.")
        }

        append(" Make the logo modern, clean, and suitable for digital platforms.")
    }


    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    ConstraintLayout(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        val (title, avatar, card, btn, bgGradient, gif, welcomeText, name, des) = createRefs()

        val horizontalGuidelines = createGuidelineFromBottom(0.55f)
        val startBarrier = createEndBarrier(name)

        BackgroundGradient(modifier = Modifier.constrainAs(bgGradient) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(horizontalGuidelines)

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })

        Text(
            text = "Welcome to,",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppins,
            textAlign = TextAlign.Justify,
            modifier = Modifier.constrainAs(welcomeText) {
                top.linkTo(title.bottom, margin = 24.dp)
                start.linkTo(parent.start, margin = 12.dp)
            }
        )

        Text(
            text = "LogoGen AI",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppins,
            textAlign = TextAlign.Justify,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(welcomeText.bottom, margin = 0.dp)
                start.linkTo(welcomeText.start)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.element),
            contentDescription = null,
            modifier = Modifier
                .size(width = 250.dp, height = 200.dp)
                .constrainAs(gif) {
                    top.linkTo(name.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(des.bottom)
                    start.linkTo(startBarrier, margin = 56.dp)
                }
        )

        Text(
            text = "Describe your brand and let AI \ncreate your perfect logo.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = poppins,
            textAlign = TextAlign.Justify,
            modifier = Modifier.constrainAs(des) {
                start.linkTo(welcomeText.start)
                top.linkTo(name.bottom, margin = 4.dp)
            }
        )


        TitleText(modifier = Modifier.constrainAs(title) {
            top.linkTo(parent.top, margin = 42.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

//        UserAvatar(modifier = Modifier.constrainAs(avatar){
//            top.linkTo(title.top)
//            bottom.linkTo(title.bottom)
//            end.linkTo(parent.end, margin = 12.dp)
//        })

        Card(
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFc1c8f3)),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(card) {
                    top.linkTo(title.bottom, margin = 180.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InputField("Brand Name", brandName, { brandName = it }) {
                    Text(
                        text = "Enter your brand name"
                    )
                }

                InputField("Tagline", tagline, { tagline = it }) {
                    Text(
                        text = "Enter your brand's tagline"
                    )
                }

                InputField("Sector", sector, { sector = it }) {
                    Text(
                        text = "Eg. FinTech, EdTech, Healthcare, FMCG"
                    )
                }

                InputField("Brand Values", brandValues, { brandValues = it }) {
                    Text(
                        text = "Eg. Trust, Sustainability, Innovation"
                    )
                }

                InputField("Style", style, { style = it }) {
                    Text(
                        text = "Eg. Minimalist, Bold, Energetic, Eco-Friendly"
                    )
                }

                InputField("Primary Colors", colors, { colors = it }) {
                    Text(
                        text = "Eg. Red, Blue, Green"
                    )
                }


            }
        }

        FilledTonalButton(onClick = { gen = true },
            modifier = Modifier

                .constrainAs(btn) {
                    top.linkTo(card.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = ButtonDefaults.filledTonalButtonColors(Color(0xFF4756DF))
        ){

            Text(text = "Generate Logo", color = Color.White, fontSize = 16.sp)
        }
    }

    if(gen){
        Log.i("MYTAG", "HomeScreen: $prompt")
        navigateToResult()
        //viewModel.generateImage(prompt)
    }
}

@Composable
fun TitleText(modifier: Modifier){
        Text(
            text = "LogoGen AI",
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
}

@Composable
fun UserAvatar(modifier: Modifier){

    Image(
        painter = painterResource(id = R.drawable.baseline_person_24),
        contentDescription = null,

        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White)
    )
}

@Composable
fun UserInputCard(navigateToResult: () -> Unit, modifier: Modifier){






}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit, placeholder : @Composable () -> Unit){
    Text(
        text = label,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        fontFamily = poppins,
        modifier = Modifier.padding(bottom = 4.dp)
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clip(RoundedCornerShape(18.dp)),
        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color(0xFFeff3fc), focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent)
    )
}

@Composable
fun BackgroundGradient(modifier: Modifier){

    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Box(modifier = modifier
        .fillMaxSize()
        .background(Color(0xFFECF2FA))) {
        Image(
            painter = painterResource(id = R.drawable.background_gradient),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

//        AsyncImage(
//            model = "https://cdn.dribbble.com/userupload/31740866/file/original-b3b6e3accb710048198b96dc590425ca.gif",
//            contentDescription = null,
//            imageLoader = imageLoader,
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier.fillMaxSize()
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(24.dp),
//            verticalArrangement = Arrangement.Center,
//        ) {
//            Text(
//                text = "Welcome to LogoGen AI",
//                style = MaterialTheme.typography.headlineMedium,
//                color = Color.White
//            )
//            // Add other UI here...
//        }
//    }
    }
}