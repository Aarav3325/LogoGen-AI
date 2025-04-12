package com.aarav.aisamadhan.screens

import ImageViewModel
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ImageGeneration(viewModel: ImageViewModel, navigateToHome: () -> Unit) {

    val context = LocalContext.current

    var errorMessage by remember { mutableStateOf("") }

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    //viewModel.error()
    val bitmap by viewModel.bitmap.collectAsState()

    val error by viewModel.error.collectAsState()

    var url by remember {
        mutableStateOf("https://i.pinimg.com/originals/54/58/a1/5458a14ae4c8f07055b7441ff0f234cf.gif")
    }


    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentScale = ContentScale.Fit,
                contentDescription = "Generated Image",
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            )
        } ?: AsyncImage(
            model = url,
            contentDescription = null,
            imageLoader = imageLoader,
            modifier = Modifier.fillMaxSize()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            saveBitmapToDownloads(context, bitmap, "logo_${System.currentTimeMillis()}.png")
        }) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Download Logo", color = Color.White)
        }


    }

    if (error == true) {
        OnErrorMessage()
        url = "i.ping"

    }


}

@RequiresApi(Build.VERSION_CODES.Q)
fun saveBitmapToDownloads(context: Context, bitmap: Bitmap?, fileName: String) {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }

    val imageUri: Uri? = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

    imageUri?.let {
        resolver.openOutputStream(it)?.use { outputStream ->
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            Toast.makeText(context, "Logo saved to Downloads", Toast.LENGTH_SHORT).show()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OnErrorMessage() {

    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 54.dp, bottom = 16.dp)
            .fillMaxWidth()
            .height(320.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE5E5)), // Light red background
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Error",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "There was an error while generating the logo",
                    color = Color(0xFFD32F2F),
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

            }

            FilledTonalButton(
                onClick = { },
                modifier = Modifier.align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            )
            {
                Text("Go To Home Screen", color = Color.White)
            }
        }
    }
}

