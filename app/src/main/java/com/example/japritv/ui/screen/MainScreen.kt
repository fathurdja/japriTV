package com.example.japritv.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.japritv.R
import com.example.japritv.model.Show
import com.example.japritv.navigation.NavGraph
import com.example.japritv.ui.components.*
import com.example.japritv.ui.components.home.CardRating
import com.example.japritv.ui.components.home.Category
import com.example.japritv.ui.components.home.CustomSearchBox
import com.example.japritv.ui.components.home.CustomTopBar
import com.example.japritv.ui.components.home.SearchResultList
import com.example.japritv.viewmodel.CategoryViewModel
import com.example.japritv.viewmodel.ShowItemViewModel
import com.example.japritv.viewmodel.VideoViewModel


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var searchText by remember { mutableStateOf("") }
    val data: ShowItemViewModel = viewModel()
    val shows by remember { mutableStateOf(data.shows) }
    var selectedItem by remember { mutableStateOf(0) }
    val categoryViewModel: CategoryViewModel = viewModel()
    val showItemViewModel: ShowItemViewModel = viewModel()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        containerColor = Color.Black,
        topBar = {
            if (currentRoute in listOf("home", "terlaris", "rating", "segera_tayang")) {
                CustomTopBar(
                    searchText = searchText,
                    onSearchTextChanged = { newText -> searchText = newText },
                    navController = navController,
                    categoryViewModel = categoryViewModel,
                    showItemViewModel = showItemViewModel
                )
            }

        },
        bottomBar = {
            if (currentRoute in listOf("home", "terlaris", "rating", "segera_tayang", "video", "upload")) {
                BottomNavigationBar(
                    selectedItem = selectedItem,
                    onItemSelected = { index ->
                        selectedItem = index
                        when (index) {
                            0 -> navController.navigate("home")
                            1 -> navController.navigate("video")
                            2 -> navController.navigate("upload")
                        }
                    },
                    navController = navController
                )
            }
        }
    ) { paddingValues ->
        NavGraph(navController = navController, paddingValues = paddingValues)
    }
}


//@Preview
//@Composable
//private fun MainScreenPreview() {
//    JapriTvTheme {
//        MainScreen()
//    }
//}
