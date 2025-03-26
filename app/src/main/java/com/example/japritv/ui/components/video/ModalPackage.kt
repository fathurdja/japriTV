package com.example.japritv.ui.components.video

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R
import com.example.japritv.ui.components.CustomBoxButton
import com.example.japritv.ui.components.profile.CoinGrid
import com.example.japritv.ui.components.profile.Keanggotaan

@Composable
fun ModalityContainer(onClick: ()-> Unit,selectedCoin: String,
                      onCoinSelected: (String, String) -> Unit) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)

    ) {
        // Header
       Box(modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth()) {
           Row(
               modifier = Modifier.fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically
           ) {
               Text(
                   text = "Episode ini: 2",
                   color = Color.Gray,
                   fontSize = 14.sp
               )

               // Icon in the middle
               Image(
                   painter = painterResource(id = R.drawable.point_solid_1),
                   contentDescription = null,
                   modifier = Modifier.padding(start = 5.dp) // Add some spacing around the icon
               )

               Text(
                   text = "|",
                   color = Color.Gray,
                   fontSize = 20.sp,
                   modifier = Modifier.padding(horizontal = 8.dp)
               )

               Text(
                   text = "Saldo: 0",
                   color = Color.Gray,
                   fontSize = 14.sp
               )

               Image(
                   painter = painterResource(id = R.drawable.point_solid_1),
                   contentDescription = null,
                   modifier = Modifier.padding(horizontal = 8.dp) // Add some spacing around the icon
               )
               Image(
                   painter = painterResource(id = R.drawable.vector__12_),
                   contentDescription = null,
                   modifier = Modifier.padding(start =115.dp).size(24.dp)
                   // Add some spacing around the icon
               )


           }
       }

        Spacer(modifier = Modifier.height(16.dp))

        // Coin options
        CoinGrid(
            selectedCoin = selectedCoin, onCoinSelected = onCoinSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weekly offer
        Keanggotaan(
            tipe = "Mingguan",
            harga = "Rp 100.000",
            hargaLama = "Rp 150.000",
            onClick = {},
            benefits = "Semua episode gratis",
            isSelected = false,
            setharga = false,
            setHarga = {},
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Monthly offer
        Keanggotaan(
            tipe = "Mingguan",
            harga = "Rp 100.000",
            hargaLama = "Rp 150.000",
            onClick = {},
            benefits = "Semua episode gratis",
            isSelected = false,
            setharga = false,
            setHarga = {}
        )
        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)){
            CustomBoxButton(colorText = Color.White, colorBackground = Color(0XFFD22F26)
                , title = "Lanjutkan", onClick = {onClick()}, modifier = Modifier)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ModalityContainerPreview() {
    ModalityContainer(onClick = {}, selectedCoin ="", onCoinSelected = { _, _ ->} )
}