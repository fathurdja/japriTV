package com.example.japritv.ui.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.japritv.model.PaymentData
import com.example.japritv.ui.components.CustomBoxButton
import com.example.japritv.ui.components.CustomBoxButtonBorder
import com.example.japritv.ui.components.payment.DetailPembayaranSubsOrCoin
import com.example.japritv.ui.components.payment.PaymentCard
import com.example.japritv.viewmodel.PaymentViewModel

@Composable
fun HistoryPembayaran(
    paymentData: PaymentData,
    onClick: () -> Unit,
    onClickBack: () -> Unit,
    colortext: Color,
    colorButton: Color,
) {
    val isLoading = false // udah dapet data, jadi ga loading

    val harga = paymentData.totalAmount
    val biayaTambahan = listOfNotNull(
        paymentData.serverFee,
        paymentData.admin,
        paymentData.unique
    ).sum()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)) {
            PaymentCard(
                nominal = harga.toString(),
                bank = paymentData.bank,
                vaName = paymentData.vaName,
                vaNumber = paymentData.vaNumber,
                status = paymentData.status,
                cancel = paymentData.isCancel ?: false
            )

            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            DetailPembayaranSubsOrCoin(
                color = Color.White,
                tipeSubs = paymentData.level ?: "",
                Amount = paymentData.amount,
                biayaTambahan = biayaTambahan,
                jumlahKoin = paymentData.amount.toString(),
                typePayment = paymentData.type,
                totalPembayaran = harga,
                biayaPerEpisode = paymentData.amount ?: 0,
                totalEpisode = paymentData.totalEpisode ?: 0
            )

            Spacer(modifier = Modifier.height(25.dp))

            CustomBoxButton(
                title = "Kembali Ke JapriTV",
                onClick = { onClick() },
                colorBackground = Color(0XFFD22F26),
                colorText = Color.White,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (paymentData.status == "pending") {
                CustomBoxButtonBorder(
                    title = "Batalkan Pembayaran",
                    onClick = { onClickBack() },
                    colorBackground = colorButton,
                    colorText = colortext,
                    modifier = Modifier
                )
            }
        }
    }
}
