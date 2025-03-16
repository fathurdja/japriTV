package com.example.japritv.viewmodel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.model.DataItem
import com.example.japritv.model.ResponseVideo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class VideoViewModel() : ViewModel() {
    private val _dataList = mutableStateListOf<DataItem>()
    val dataList: List<DataItem> = _dataList

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true  // Ignore unexpected fields in the response
                isLenient = true           // Allow for relaxed parsing (useful for handling unexpected formats)
                prettyPrint = true
            })
        }
    }

    fun fetchVideos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: ResponseVideo = client.get("http://185.250.38.224:3000/api/video").body()
                println(response)
                if (!response.error) {
                    _dataList.clear()
                    _dataList.addAll(response.data)
                } else {
                    println("API Error: ${response.message}")
                    // Handle API error (e.g., show a Snackbar)
                }
            } catch (e: Exception) {
                println("Network Error: ${e.message}")
                // Handle network error (e.g., show a Snackbar)
            } finally {
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        client.close()
    }


}
