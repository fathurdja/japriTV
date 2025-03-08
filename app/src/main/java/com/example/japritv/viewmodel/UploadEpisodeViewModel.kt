package com.example.japritv.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.model.Episode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UploadEpisodeViewModel : ViewModel()  {
    private val _episodes = mutableStateListOf<Episode>()

    val episodes: List<Episode> = _episodes

    // Add an episode to the list
    init {
        _episodes.add(Episode(
            movieTitle = "Episode 1",
            episodeTitle = "Episode 1",
            fileName = "",
            fileSize = "",
            isUploading = false,
        ))
    }

    fun uploadFile(episodeIndex: Int, fileName: String, fileSize: String) {
        val episode = _episodes[episodeIndex]
        _episodes[episodeIndex] = episode.copy(isUploading = true, fileName = "", fileSize = "", progress = 0f)

        viewModelScope.launch {
            for (i in 1..100) {
                delay(50) // Simulasi upload delay
                _episodes[episodeIndex] = _episodes[episodeIndex].copy(progress = i / 100f)
            }
            _episodes[episodeIndex] = _episodes[episodeIndex].copy(isUploading = false, fileName = fileName, fileSize = fileSize, progress = 1f)
        }
    }

    // Update the progress of an episode
    fun updateEpisodeProgress(index: Int, progress: Float) {
        val episode = _episodes.getOrNull(index) ?: return
        _episodes[index] = episode.copy(progress = progress)
    }
    fun updateMovieTitle(index: Int, newTitle: String) {
        _episodes[index] = _episodes[index].copy(movieTitle = newTitle)
    }

    // Example function to reset all episodes to 0% progress
    fun resetAllEpisodesProgress() {
        _episodes.forEachIndexed { index, episode ->
            _episodes[index] = episode.copy(progress = 0f)
        }
    }

    fun addEpisode() {
        _episodes.add(Episode(
            episodeTitle = "Episode ${_episodes.size + 1}",
            movieTitle = "Episode 1",
            fileName = "",
            fileSize = "",
            isUploading = false,)

        )
    }

}