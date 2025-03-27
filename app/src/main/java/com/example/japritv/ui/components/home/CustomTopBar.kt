package com.example.japritv.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.japritv.viewmodel.CategoryViewModel
import com.example.japritv.viewmodel.ShowItemViewModel
import com.example.japritv.viewmodel.VideoViewModel

@Composable
fun CustomTopBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    navController: NavController,
    categoryViewModel: CategoryViewModel,
    showItemViewModel: ShowItemViewModel,
    videoViewModel: VideoViewModel
) {
    // Use remember with categories to track state changes
    val categories by remember { mutableStateOf(categoryViewModel.categories) }
    val  shows by remember { mutableStateOf(showItemViewModel.shows) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(horizontal = 5.dp, vertical = 5.dp)
    ) {
        CustomSearchBox(
            searchText = searchText,
            onTextChanged = onSearchTextChanged,
            onClearClick = {
                onSearchTextChanged("")
                categoryViewModel.resetSelection()
            }
        )

        if (searchText.isEmpty()) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    Category(
                        text = category.name,
                        backgroundColor = if (category.isSelected) Color.Red else Color(0xFF343434),
                        onClick = {
                            categoryViewModel.selectCategory(category)
                            when (category.name) {
                                "🔥 Terlaris" -> navController.navigate("terlaris")
                                "Daftar Peringkat" -> navController.navigate("rating")
                                "Segera Tayang" -> navController.navigate("segera_tayang")
                            }
                        }
                    )
                }
            }
        }
        if (searchText.isNotEmpty()) {
            SearchResultList(
                videoViewModel = videoViewModel
            )
        }
    }
}