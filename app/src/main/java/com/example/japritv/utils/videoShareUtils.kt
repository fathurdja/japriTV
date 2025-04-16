package com.example.japritv.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

suspend fun downloadVideoToCache(context: Context, videoId: String, token: String): File? = withContext(Dispatchers.IO) {
    try {
        val url = URL("https://tv.japrime.id/video/watch/$videoId")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            setRequestProperty("Authorization", token)
            connect()
        }

        val file = File(context.cacheDir, "shared_video_${videoId}.mp4")
        connection.inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun shareVideoLink(context: Context, videoId: String,episode:Int) {
    val url = "https://tv.japrime.id/video/$videoId/$episode"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    context.startActivity(Intent.createChooser(intent, "Share Video Link"))
}
fun generateQRCode(url: String): Bitmap {
    val width = 500
    val height = 500
    val bitMatrix: BitMatrix = MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height)
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }

    return bitmap
}
fun shareLinkReferral(context: Context, url: String) {
    val message = "Yuk pakai link referral saya untuk mendapatkan bonus koin:\n\n$url"

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }

    val chooser = Intent.createChooser(intent, "Bagikan Link Referral")
    context.startActivity(chooser)
}

fun hitWatchUrl(videoId: String, token: String) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://tv.japrime.id/video/watch/$videoId")
                .post(okhttp3.RequestBody.create(null, ByteArray(0))) // body kosong
                .addHeader("Authorization", token)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    println("Failed to hit watch url: ${response.code}")
                    Log.e("HIT_WATCH_URL", "Failed to hit watch url: ${response.code}")
                } else {
                    println("Watch URL hit successful for videoId: $videoId")
                    Log.d("HIT_WATCH_URL", "Watch URL hit successful for videoId: $videoId")
                }
            }
        } catch (e: Exception) {
            println("Error hitting watch url: ${e.message}")
            Log.e("HIT_WATCH_URL", "Error hitting watch url: ${e.message}")
        }
    }
}


