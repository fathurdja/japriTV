package com.example.japritv.ui.components.video

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R

@Composable
fun ContainerEpisode(totalEpisodes: Int, selectedEpisode: Int, onEpisodeSelected: (Int) -> Unit) {
    val episodesPerPage = 25 // Maksimal 5 baris x 5 episode
    val totalTabs = (totalEpisodes / episodesPerPage) + if (totalEpisodes % episodesPerPage > 0) 1 else 0
    val tabTitles = List(totalTabs) { index ->
        val start = index * episodesPerPage + 1
        val end = minOf((index + 1) * episodesPerPage, totalEpisodes)
        "$start-$end"
    }

    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.title_card),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Money Heist: Korea. Joint Economic Area",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = "Total $totalEpisodes Episode", color = Color.Gray, fontSize = 14.sp)
                Row {
                    Chip(text = "Action")
                    Spacer(modifier = Modifier.width(8.dp))
                    Chip(text = "Crime")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Tab untuk memilih rentang episode
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = Color.Gray,
            divider = {},
            indicator = {}
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTabIndex == index) Color.White else Color.Gray
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Menampilkan episode berdasarkan tab yang dipilih
        val startEpisode = selectedTabIndex * episodesPerPage + 1
        val endEpisode = minOf((selectedTabIndex + 1) * episodesPerPage, totalEpisodes)
        val episodes = (startEpisode..endEpisode).toList()

        Column {
            episodes.chunked(5).forEach { rowEpisodes ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowEpisodes.forEach { episodeNumber ->
                        val isLocked = episodeNumber > totalEpisodes
                        EpisodeButton(
                            episodeNumber = episodeNumber,
                            isLocked = isLocked,
                            isSelected = episodeNumber == selectedEpisode,
                            onClick = { if (!isLocked) onEpisodeSelected(episodeNumber) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun Chip(text: String) {
    Box(
        modifier = Modifier
            .background(Color.DarkGray, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun EpisodeButton(episodeNumber: Int, isLocked: Boolean, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .width(64.dp)
            .height(36.dp)
            .background(
                when {
                    isSelected ->  Color(0xFFD22F26)
                    isLocked -> Color(0xFF565656)
                    else -> Color(0xFF565656)
                },
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
            .clickable(enabled = !isLocked, onClick = onClick)
    ) {
        if (isLocked){
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = episodeNumber.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painterResource(id = R.drawable.material_symbols_light_lock),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }else{
            Text(
                text = episodeNumber.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ContainerEpisodePreview() {
    ContainerEpisode(onEpisodeSelected = {}, selectedEpisode = 1, totalEpisodes = 50)
}

