package com.example.japritv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.R
import com.example.japritv.Repository.ProfileRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.model.subscriptionData
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {
    private val _paymentMethods = MutableLiveData<List<PaymentCategory>>()
    val paymentMethods: LiveData<List<PaymentCategory>> = _paymentMethods

    val subscriptionInfo = MutableLiveData<subscriptionData?>()
    val isSubscriptionUpdated = MutableLiveData<Boolean>()

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


    suspend fun getDataSubscription( db: AppDatabase, ){
        val response = ProfileRepository.getDataSubscription( db)
        subscriptionInfo.value = response

    }
//
    suspend fun makeTransactionVideo(db: AppDatabase, amount: Int, idCreator: String){
        viewModelScope.launch {
            ProfileRepository.makeDataTransaction(db = db, amount = amount, idCreator = idCreator)
        }
    }
}



// Data class untuk kategori dan item pembayaran
data class PaymentCategory(val title: String, val items: List<PaymentItem>)
data class PaymentItem(val iconRes: Int, val name: String)
