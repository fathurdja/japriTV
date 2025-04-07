package com.example.japritv.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.Repository.ProfileRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.model.Episode
import com.example.japritv.model.UploadVideoData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


import okhttp3.RequestBody.Companion.asRequestBody

class UploadEpisodeViewModel( db: AppDatabase) : ViewModel()  {

    private val loginInfoDao = db.loginInfoDao()

    private suspend fun getTokenFromDatabase(): String? {
        return loginInfoDao.getTokenAuth()
    }
    private val _episodes = mutableStateListOf<Episode>()
    val episodes: List<Episode> = _episodes

    // Add an episode to the list
    init {
        _episodes.add(Episode(
            movieTitle = "",
            episodeTitle = "Episode 1",
            fileName = null,
            fileSize = "",
            isUploading = false,
            thumbnail = null,
        ))
    }



    fun getFileFromUri(context: Context, uri: Uri): File? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.cacheDir, "${System.currentTimeMillis()}.mp4")
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return file
    }
    fun uploadVideoToServer(
        title: String,
        videoFiles: List<File>,
        onSuccess: (String,String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val token = getTokenFromDatabase() ?: run {
                onFailure("Token not found")
                return@launch
            }

            val client = OkHttpClient()

            // Konversi Video ke MultipartBody
            val videoParts = videoFiles.map { file ->
                val requestBody = RequestBody.create("video/mp4".toMediaTypeOrNull(), file)
                MultipartBody.Part.createFormData("video", file.name, requestBody)
            }

            // Simpan poster ke file jika ada
            val posterFile = getVideoThumbnailFromFile(videoFiles.firstOrNull() ?: return@launch)
            val posterPart = posterFile?.let { file ->
                val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
                MultipartBody.Part.createFormData("poster", file.name, requestBody)
            }


            // Tambahkan field lain dengan RequestBody
            val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
            // Bangun MultipartBody
            val requestBodyBuilder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", null, titlePart)


            // Tambahkan video dan poster
            videoParts.forEach { requestBodyBuilder.addPart(it) }
            posterPart?.let { requestBodyBuilder.addPart(it) }


            val requestBody = requestBodyBuilder.build()
            val requestBodyDebug = requestBody.parts.joinToString("\n") { part ->
                "Part Name: ${part.headers?.get("Content-Disposition")}, " +
                        "Content Type: ${part.body.contentType()}, " +
                        "Body Size: ${part.body.contentLength()} bytes"
            }
            Log.d("Upload", "Request Body:\n$requestBodyDebug")
            // Buat request ke server
            val request = Request.Builder()
                .url("https://tv.japrime.id/creator/upload")
                .post(requestBody)
                .addHeader("Authorization", token)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Upload", "Upload Failed: ${e.message}")
                    onFailure(e.message ?: "Unknown error")
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (it.isSuccessful) {
                            val responseBody = it.body?.string() ?: ""
                            Log.d("Upload", "Upload Success: $responseBody")

                            try {
                                val json = JSONObject(responseBody)
                                val data = json.optJSONObject("data")
                                val videoId = data?.optString("_id") ?: ""
                                val totalEpisode = data?.optInt("total_episode") ?: ""


                                Log.d("Upload", "Video ID: $videoId")
                                onSuccess(videoId,totalEpisode.toString())
                            } catch (e: Exception) {
                                Log.e("Upload", "Failed to parse _id: ${e.message}")
                                onFailure("Failed to parse _id")
                            }
                        } else {
                            val errorBody = it.body?.string() ?: "Unknown error"
                            Log.e("Upload", "Upload Error: $errorBody")
                            onFailure(errorBody)
                        }
                    }
                }

            })
        }
    }
    fun uploadFile(episodeIndex: Int, file: File, fileSize: String, poster: File) {
        val episode = _episodes[episodeIndex]
        _episodes[episodeIndex] = episode.copy(
            isUploading = true,
            fileName = null,  // Kosongkan dulu saat upload mulai
            fileSize = "",
            progress = 0f,
            thumbnail = poster
        )

        viewModelScope.launch {
            for (i in 1..100) {
                delay(50) // Simulasi upload delay
                _episodes[episodeIndex] = _episodes[episodeIndex].copy(progress = i / 100f)
            }
            _episodes[episodeIndex] = _episodes[episodeIndex].copy(
                isUploading = false,
                fileName = file,  // Simpan file setelah upload selesai
                fileSize = fileSize,
                progress = 1f,
                thumbnail = poster
            )
        }
    }
    fun updateMovieTitle(index: Int, newTitle: String) {
        _episodes[index] = _episodes[index].copy(movieTitle = newTitle)
    }
    fun addEpisode() {
        _episodes.add(Episode(
            episodeTitle = "Episode ${_episodes.size + 1}",
            movieTitle = "Episode 1",
            fileName = null,
            fileSize = "",
            isUploading = false,
            thumbnail = null,

            ))
    }
    fun getVideoThumbnailFromFile(videoFile: File): File? {
        return try {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(videoFile.absolutePath) // Ambil data dari file path

            // Ambil frame di detik ke-1
            val bitmap: Bitmap? = retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)

            retriever.release()

            if (bitmap != null) {
                // Simpan thumbnail ke file
                val thumbnailFile = File(videoFile.parent, "thumbnail_${videoFile.nameWithoutExtension}.jpg")
                FileOutputStream(thumbnailFile).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
                }
                thumbnailFile
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getFileSize(context: Context, uri: Uri): String {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return if (cursor != null && cursor.moveToFirst()) {
            val sizeIndex = cursor.getColumnIndex(MediaStore.Video.Media.SIZE)
            val size = if (sizeIndex >= 0) cursor.getLong(sizeIndex) else 0L
            cursor.close()
            "${size / (1024 * 1024)} MB" // Konversi ke MB
        } else {
            "0 MB"
        }
    }
    fun deleteUploadedFile(episodeIndex: Int) {
        val episode = _episodes.getOrNull(episodeIndex) ?: return

        // Hapus file video jika ada
        episode.fileName?.let { file ->
            if (file.exists()) {
                file.delete()
                Log.d("DeleteFile", "File ${file.name} berhasil dihapus")
            }
        }

        // Hapus thumbnail jika ada
        episode.thumbnail?.let { file ->
            if (file.exists()) {
                file.delete()
                Log.d("DeleteFile", "Thumbnail ${file.name} berhasil dihapus")
            }
        }

        // Reset episode setelah file dihapus
        _episodes[episodeIndex] = episode.copy(
            fileName = null,
            fileSize = "",
            progress = 0f,
            isUploading = false,
            thumbnail = null
        )
    }

}
