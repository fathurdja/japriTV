package com.example.japritv.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.japritv.R
import com.example.japritv.ui.components.CustomBoxButton
import com.example.japritv.ui.components.HeaderRightWithIcon
import com.example.japritv.ui.components.ScaffoldWithButton
import com.example.japritv.ui.components.ScaffoldWithoutButton
import com.example.japritv.ui.components.profile.CoinGrid
import com.example.japritv.ui.components.profile.Keanggotaan
import com.example.japritv.viewmodel.UserViewModel

@Composable
fun TokoJapriTV(userViewModel: UserViewModel) {
    val selectedMembership by userViewModel.selectedMembership.collectAsState()
    val nominal by userViewModel.nominal.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        horizontalAlignment = Alignment.Start
    ) {
        // Koin Section
        Text(
            text = "Koin",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, top = 30.dp, bottom = 8.dp)
        )

        // Coin Grid
        CoinGrid(
            selectedCoin = selectedMembership ?: "",
            onCoinSelected = { coin, price ->
                userViewModel.setSelectedMembership(coin)
                userViewModel.setNominal(price.replace("Rp ", "").replace(".", "").toInt())
            }
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Anggota Section
        Text(
            text = "Anggota",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        MembershipOptions(
            selectedMembership = selectedMembership ?: "",
            nominal = nominal ?: 0,// Pastikan selectedMembership tidak null
            onSelect = { userViewModel.setSelectedMembership(it)
                        userViewModel.setNominal(nominal)},
            harga = { userViewModel.setNominal(nominal) } // Pastikan fungsi harga menerima nilai
        )

        // Membership Cards



//       Box(modifier = Modifier.fillMaxWidth().padding(top = 150.dp, start = 20.dp, end = 20.dp)) {
//
//              CustomBoxButton(
//                  colorBackground = Color(0xFF6B6B6B),
//                  colorText = Color.White,
//                  title = "lanjut ke Pembayaran",
//                  onClick = {},
//                  modifier = Modifier.align(Alignment.BottomCenter)
//              )
//
//       }
    }
}


@Composable
fun MembershipOptions(selectedMembership: String, nominal:Int,onSelect: (String) -> Unit, harga:(Int)->Unit) {
    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
        Keanggotaan(
            tipe = "Mingguan",
            harga = "Rp 100.000",
            hargaLama = "Rp 99.000",
            benefits = "Untuk 50 judul video",
            isSelected = selectedMembership == "mingguan",
            onClick = {
                onSelect("mingguan")

            },
            setharga = nominal == 100000,
            setHarga = {harga(100000)}
        )
        Spacer(modifier = Modifier.height(14.dp))
        Keanggotaan(
            tipe = "Bulanan",
            harga = "Rp 250.000",
            hargaLama = "Rp 500.000",
            benefits = "Semua episode bisa ditonton gratis",
            isSelected = selectedMembership == "bulanan",
            onClick = { onSelect("bulanan") },
            setharga = nominal == 250000,
             setHarga = {harga(250000)}
        )
    }
}
@Preview
@Composable
private fun TokoJapriTvPreview() {
    val userViewModel:UserViewModel = viewModel()
    val navController= rememberNavController()
    ScaffoldWithButton(
        navController = navController ,
        containerColor = Color.Black,
        navigationRoute = "",
        content = {TokoJapriTV(userViewModel)},
        titleButton = "Lanjut Ke Pembayaran",
        contentTop = {HeaderRightWithIcon(
            title = "Toko Japri Tv",
            color = Color.Black,
            textColor = Color.White,
            resId = R.drawable.vector__9_,
            onBackClick = {}
        )},
        colorButton = Color.Red,
        colorTextButton = Color.White,
        modifier = Modifier,
        onClick = {}
    )
}
@Preview
@Composable
private fun TokoJapriTvPreview2() {
    val userViewModel:UserViewModel = viewModel()
    val navController= rememberNavController()
    ScaffoldWithoutButton(
        containerColor = Color.Black,
        content = {TokoJapriTV(userViewModel

        )},
        contentTop = { HeaderRightWithIcon(
            title = "Toko Japri Tv",
            color = Color.Black,
            textColor = Color.White,
            resId = R.drawable.arrowwhite,
            onBackClick = {}
        ) }
    )
}

