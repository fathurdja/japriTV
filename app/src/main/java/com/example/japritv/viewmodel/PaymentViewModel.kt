package com.example.japritv.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japritv.R
import com.example.japritv.Repository.ProfileRepository
import com.example.japritv.dao.AppDatabase
import com.example.japritv.model.PaymentData
import com.example.japritv.model.subscriptionData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(private val db: AppDatabase) : ViewModel() {
    private val _paymentMethods = MutableLiveData<List<PaymentCategory>>()
    val paymentMethods: LiveData<List<PaymentCategory>> = _paymentMethods
    private val _transactionInfo = MutableStateFlow<PaymentData?>(null)
    val transactionInfo: StateFlow<PaymentData?> = _transactionInfo


    init {
        // Simulasi data dari API
        _paymentMethods.value = listOf(
            PaymentCategory(
                title = "Pembayaran Instan / E-Wallet",
                items = listOf(
                    PaymentItem(R.drawable.gopay, "GoPay",""),

                ),
                type = "qr"
            ),
            PaymentCategory(
                title = "Virtual Account",
                items = listOf(
                    PaymentItem(R.drawable._09091_1, "Transfer Bank BCA","BCA"),

                ),
                type = "va"
            ),
            PaymentCategory(
                title = "Transfer Bank",
                items = emptyList(),
                type = ""
            )

        )
    }

    fun getDataTransaction() {
        viewModelScope.launch {
            val transactions = ProfileRepository.getDataTransaction(db = db)
            _transactionInfo.value = transactions

        }
    }
    fun deleteTransaction(){
        viewModelScope.launch {

        }
    }
    fun makePaymentVideo(type:String,db: AppDatabase,idVideo:String,bank:String,onSuccess: () -> Unit,
                               onError: () -> Unit){
        viewModelScope.launch {
            val result = ProfileRepository.makeDataTransactionVideo(type,idVideo,bank,db)
            if (result != null) {
                _transactionInfo.value = result
                onSuccess()
            } else {
                onError()
            }
        }
    }

    fun topUpSaldoSubscription(type:String,db: AppDatabase,level:String,bank:String,onSuccess: () -> Unit,
                       onError: () -> Unit){
        viewModelScope.launch {
            val result = ProfileRepository.makeSubscription(level,db,type,bank)
            if (result != null) {
                _transactionInfo.value = result
                onSuccess()
            } else {
                onError()
            }
        }
    }
    fun topUpSaldoKoin(type:String,db: AppDatabase,amount:Int,bank:String,onSuccess: () -> Unit,
                       onError: () -> Unit){
        viewModelScope.launch {
            val result = ProfileRepository.topUpSaldo(type, amount, db, bank)
            if (result != null) {
                _transactionInfo.value = result
                onSuccess()
            } else {
                onError()
            }
        }
    }

}



// Data class untuk kategori dan item pembayaran
data class PaymentCategory(val title: String, val items: List<PaymentItem>,val type:String)
data class PaymentItem(val iconRes: Int, val name: String, val value:String )
