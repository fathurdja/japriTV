package com.example.japritv.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.ui.components.ScaffoldWithoutButton
import com.example.japritv.ui.components.home.Category
import com.example.japritv.ui.components.profile.UserInfoCard
import com.example.japritv.ui.theme.JapriTvTheme

@Composable
fun RiwayatPembelian(
    navController: NavController,
) {

    data class menuRiwayat(
        val name: String,
        val isSelected: Boolean = false
    )
    val menu= listOf(
        menuRiwayat(name = "Pembelian Video",isSelected = true),
        menuRiwayat(name = "Pembelian Keanggotaan",isSelected = false)
    )
   Column(modifier = Modifier.padding(16.dp)) {
       LazyRow(
           modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
           horizontalArrangement = Arrangement.spacedBy(8.dp)
       ) {
           items(menu) { category ->
               Category(
                   text = category.name,
                   backgroundColor = if (category.isSelected) Color.Red else Color(0xFF343434),
                   onClick = {
                       when (category.name) {
                           "Pembelian Video" -> navController.navigate("terlaris")
                           "Pembelian Keanggotaan" -> navController.navigate("rating")

                       }
                   }
               )
           }
       }

       Spacer(modifier = Modifier.height(10.dp))
       LazyColumn {
           item {

               Box(modifier = Modifier) {
                   UserInfoCard(
                       movieName = "Squid Game",
                       date = "12/12/2025",
                       harga = "Rp 100.00",
                       image = R.drawable.theaters,
                   )
               }

           }
       }
   }
}

@Preview
@Composable
private fun RiwayatPembelianPreview() {
    JapriTvTheme {
        ScaffoldWithoutButton(
            content = {
                RiwayatPembelian(navController = NavController(LocalContext.current))
            },
            contentTop ={},
            containerColor = Color.Black
        )

    }
}