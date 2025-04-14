package com.example.japritv.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    val url = "japritv://watch/$videoId/episode/$episode"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    context.startActivity(Intent.createChooser(intent, "Share Video Link"))
}
fun shareLinkReferral(context: Context, url: String) {

    val message = "Yuk Pakai link Referral Saya untuk mendapatkan Bonus Koin: $url"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    val chooser = Intent.createChooser(intent, "Share Link Referral")
    context.startActivity(chooser)
}

