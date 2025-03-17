package com.example.japritv.ui.screen

import DetailPembayaranInteractive
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.ui.components.CustomBoxButton
import com.example.japritv.ui.components.CustomBoxButtonBorder
import com.example.japritv.ui.components.HeaderRightWithIcon
import com.example.japritv.ui.components.ScaffoldWithoutButton
import com.example.japritv.ui.components.payment.PaymentCard
import com.example.japritv.ui.theme.JapriTvTheme

@Composable
fun InstruksiBayarScreen(modifier: Modifier = Modifier,onClick :()->Unit,onClickBack:()->Unit, colortext:Color,colorButton:Color) {
    Box(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Column(modifier=Modifier.padding(vertical = 20.dp)) {
            PaymentCard()
            Spacer(modifier = Modifier.padding(vertical = 15.dp))
            DetailPembayaranInteractive(color = Color.White)
            Spacer(modifier = Modifier.height(25.dp))
            CustomBoxButton(title = "Kembali Ke JapriTV", onClick = {onClick()},colorBackground = Color(0XFFD22F26), colorText = Color.White, modifier = modifier)
            Spacer(modifier = Modifier.height(20.dp))
            CustomBoxButtonBorder(title = "Batalkan Pembayaran", onClick = {onClickBack()},colorBackground = colorButton, colorText = colortext, modifier = modifier)
        }

    }
}

//@Preview
//@Composable
//private fun InstruksiBayarScreenPreview() {
//    JapriTvTheme {
//       ScaffoldWithoutButton(
//           contentTop = { HeaderRightWithIcon(
//               title = "Instruksi Pembayaran",
//               color = Color.White,
//               textColor = Color.Black,
//               resId = R.drawable.vector__9_,
//               onBackClick = {},
//           ) },
//           content = {InstruksiBayarScreen(onClick = {}, onClickBack = {})}
//       )
//    }
//}