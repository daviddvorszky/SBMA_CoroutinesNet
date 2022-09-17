package com.example.sbma_coroutinesnet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageURL = URL("https://cdn.britannica.com/91/181391-050-1DA18304/cat-toes-paw-number-paws-tiger-tabby.jpg")
        val image: MutableState<Bitmap?> = mutableStateOf(null)
        lifecycleScope.launch(Dispatchers.Main){
            image.value = getImage(imageURL)
        }

        setContent {
            if(image.value == null){
                Text("Loading image")
            }else {
                ShowImage(image.value!!)
            }
        }
    }

    private suspend fun getImage(url: URL): Bitmap =
        withContext(Dispatchers.IO) {
            val img = BitmapFactory.decodeStream(url.openStream())
            Log.i("pengb", "Image downloaded")
            return@withContext img
        }
}

@Composable
fun ShowImage(img: Bitmap) {
        Image(img.asImageBitmap(), "")
}