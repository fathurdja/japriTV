package com.example.japritv.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.ui.theme.JapriTvTheme


@Composable
fun BottomNavigationBar(selectedItem: Int, onItemSelected: (Int) -> Unit, navController: NavController) {
    val items = listOf(
        Triple(R.drawable.beranda, "Beranda", "home"),
        Triple(R.drawable.video, "Video", "video"),
        Triple(R.drawable.uploadvideo, "Upload Video", "upload"),
        Triple(R.drawable.riwayat, "Riwayat", "history"),
        Triple(R.drawable.profile, "Profil", "profile")
    )

    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    onItemSelected(index)
                    navController.navigate(item.third){
                        popUpTo("home") { inclusive = true }
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = item.first),
                        contentDescription = item.second,
                        modifier = Modifier.size(70.dp),
                        colorFilter = ColorFilter.tint(
                            if (selectedItem == index) Color.Red else Color.Gray
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


//@Preview
//@Composable
//private fun BottomNavigationBarPreview() {
//    JapriTvTheme {
//        BottomNavigationBar(selectedItem = 0, onItemSelected = {})
//    }
//}
