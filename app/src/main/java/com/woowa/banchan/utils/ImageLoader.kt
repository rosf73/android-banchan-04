package com.woowa.banchan.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

val savedAvatars = mutableMapOf<String, Bitmap>()

fun urlToBitmap(url: String, scope: CoroutineScope, completed: (Bitmap?) -> Unit) {
    if (url == "") {
        completed(null)
        return
    }

    if (savedAvatars.containsKey(url)) {
        completed(savedAvatars[url])
        return
    }

    scope.launch(Dispatchers.IO) {
        try {
            val bitmap = BitmapFactory.decodeStream(URL(url).openStream())
            savedAvatars[url] = bitmap
            withContext(Dispatchers.Main) {
                completed(bitmap)
            }
        } catch (e: IOException) {
            withContext(Dispatchers.Main) {
                completed(null)
            }
        }
    }
}