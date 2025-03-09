package com.example.japritv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.japritv.R

class PaymentViewModel : ViewModel() {
    private val _paymentMethods = MutableLiveData<List<PaymentCategory>>()
    val paymentMethods: LiveData<List<PaymentCategory>> = _paymentMethods

    init {
        // Simulasi data dari API
        _paymentMethods.value = listOf(
            PaymentCategory(
                title = "Pembayaran Instan / E-Wallet",
                items = listOf(
                    PaymentItem(R.drawable.gopay, "GoPay"),
                    PaymentItem(R.drawable.gopay, "GoPay"),
                    PaymentItem(R.drawable.gopay, "GoPay"),
                    PaymentItem(R.drawable.gopay, "GoPay"),
                    PaymentItem(R.drawable.gopay, "GoPay"),

                )
            ),
            PaymentCategory(
                title = "Transfer Bank",
                items = listOf(
                    PaymentItem(R.drawable._09091_1, "Transfer Bank BCA"),
                    PaymentItem(R.drawable._09091_1, "Transfer Bank BCA"),
                    PaymentItem(R.drawable._09091_1, "Transfer Bank BCA"),
                    PaymentItem(R.drawable._09091_1, "Transfer Bank BCA"),
                    PaymentItem(R.drawable._09091_1, "Transfer Bank BCA"),


                )
            ),
            PaymentCategory(
                title = "Virtual Account",
                items = emptyList() // Kosong dulu untuk simulasi
            )
        )
    }
}

// Data class untuk kategori dan item pembayaran
data class PaymentCategory(val title: String, val items: List<PaymentItem>)
data class PaymentItem(val iconRes: Int, val name: String)
