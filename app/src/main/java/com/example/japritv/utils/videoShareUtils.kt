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

fun shareVideo(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "video/mp4"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    val shareIntent = Intent.createChooser(intent, null)
    context.startActivity(shareIntent)
}
