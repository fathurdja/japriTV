package com.example.japritv.ui.screen

import DetailPembayaranInteractive
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.japritv.R
import com.example.japritv.dao.AppDatabase
import com.example.japritv.model.PaymentData
import com.example.japritv.model.subscriptionData
import com.example.japritv.ui.components.CustomBoxButton
import com.example.japritv.ui.components.CustomBoxButtonBorder
import com.example.japritv.ui.components.HeaderRightWithIcon
import com.example.japritv.ui.components.ScaffoldWithoutButton
import com.example.japritv.ui.components.payment.DetailPembayaranSubsOrCoin
import com.example.japritv.ui.components.payment.PaymentCard
import com.example.japritv.ui.theme.JapriTvTheme
import com.example.japritv.viewmodel.PaymentViewModel
import kotlinx.coroutines.delay

@Composable
fun InstruksiBayarScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onClickBack: () -> Unit,
    colortext: Color,
    colorButton: Color,
    paymentViewModel: PaymentViewModel,
) {
    val transactionData by paymentViewModel.transactionInfo.collectAsState()
    val isLoading = transactionData == null

    LaunchedEffect(true) {
        paymentViewModel.getDataTransaction()
    }

    // Box utama harus full size agar bisa center-in loading
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = if (isLoading) Alignment.Center else Alignment.TopStart
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)) {
                if (transactionData != null) {
                    val harga = transactionData!!.totalAmount
                    val biayaTambahan =
                        transactionData!!.serverFee + transactionData!!.admin + transactionData!!.unique

                    PaymentCard(
                        nominal = harga.toString(),
                        bank = transactionData!!.bank,
                        vaName = transactionData!!.vaName,
                        vaNumber = transactionData!!.vaNumber,
                    )

                    Spacer(modifier = Modifier.padding(vertical = 15.dp))

                    DetailPembayaranSubsOrCoin(
                        color = Color.White,
                        tipeSubs = transactionData!!.level ?:"",
                        Amount = transactionData!!.amount,
                        biayaTambahan = biayaTambahan,
                        jumlahKoin = transactionData!!.amount.toString(),
                        typePayment = transactionData!!.name,
                        totalPembayaran = harga
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    CustomBoxButton(
                        title = "Kembali Ke JapriTV",
                        onClick = { onClick() },
                        colorBackground = Color(0XFFD22F26),
                        colorText = Color.White,
                        modifier = modifier
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    CustomBoxButtonBorder(
                        title = "Batalkan Pembayaran",
                        onClick = { onClickBack() },
                        colorBackground = colorButton,
                        colorText = colortext,
                        modifier = modifier
                    )
                } else {
                    Text("Data tidak ditemukan atau gagal memuat.")
                }
            }
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