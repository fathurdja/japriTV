package com.example.japritv.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.japritv.model.Episode

class UploadEpisodeViewModel : ViewModel()  {
    private val _episodes = mutableStateListOf<Episode>()

    val episodes: List<Episode> = _episodes

    // Add an episode to the list
    fun addEpisode(episode: Episode) {
        _episodes.add(episode)
    }

    // Update the progress of an episode
    fun updateEpisodeProgress(index: Int, progress: Float) {
        val episode = _episodes.getOrNull(index) ?: return
        _episodes[index] = episode.copy(progress = progress)
    }

    // Example function to reset all episodes to 0% progress
    fun resetAllEpisodesProgress() {
        _episodes.forEachIndexed { index, episode ->
            _episodes[index] = episode.copy(progress = 0f)
        }
    }
    init {
        addEpisode(Episode())  // Adding the default episode when the ViewModel is initialized
    }
}