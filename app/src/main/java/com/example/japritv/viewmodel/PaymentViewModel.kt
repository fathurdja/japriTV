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
import com.example.japritv.converters.PaymentBankConverter
import com.example.japritv.dao.AppDatabase
import com.example.japritv.dao.PaymentDataEntity
import com.example.japritv.model.PaymentData
import com.example.japritv.model.subscriptionData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(private val db: AppDatabase) : ViewModel() {
    private val _paymentMethods = MutableLiveData<List<PaymentCategory>>()
    val paymentMethods: LiveData<List<PaymentCategory>> = _paymentMethods
    private val _transactionInfo = MutableStateFlow<PaymentDataEntity?>(null)
    val transactionInfo: StateFlow<PaymentDataEntity?> = _transactionInfo
    private val _historyTransVideo = MutableStateFlow<List<PaymentData>?>(null)
    val historyTransVideo: StateFlow<List<PaymentData>?> = _historyTransVideo
    private val _historyTransSubscription = MutableStateFlow<List<PaymentData>?>(null)
    val historyTransSubscription: StateFlow<List<PaymentData>?> = _historyTransSubscription
    private val _historyTransCoin = MutableStateFlow<List<PaymentData>?>(null)
    val historyTransCoin: StateFlow<List<PaymentData>?> = _historyTransCoin

    init {
        fetchPaymentConfig()
    }

    private fun fetchPaymentConfig() {
        viewModelScope.launch {
            val config = db.paymentDataclass().getConfig()
            config?.let {
                val bankList = PaymentBankConverter().toList(it.banks)

                val bankItems = bankList.mapNotNull { bank ->
                    val status = if (bank.online) "Available" else "Not Available"
                    when (bank.name.uppercase()) {
                        "BCA" -> PaymentItem(R.drawable.bank_central_asia, "Transfer Bank BCA", "BCA", bank.online, status)
                        "BNI" -> PaymentItem(R.drawable.bank_bni_logo, "Transfer Bank BNI", "BNI", bank.online, status)
                        "BRI" -> PaymentItem(R.drawable.bank_rakyat_indonesia_logo, "Transfer Bank BRI", "BRI", bank.online, status)
                        "MANDIRI" -> PaymentItem(R.drawable.bank_mandiri_logo_2016, "Transfer Bank Mandiri", "MANDIRI", bank.online, status)
                        else -> null
                    }
                }

                _paymentMethods.value = listOf(
                    PaymentCategory(
                        title = "Virtual Account",
                        items = bankItems,
                        type = "va"
                    )
                    ,
                    PaymentCategory(
                        title = "QRIS",
                        items = listOf(PaymentItem(R.drawable.qris, "QRIS", "QRIS",status = "Not Available")),
                        type = "qris")
                )
            }
        }
    }


    fun getDataTransaction() {
        viewModelScope.launch {
            val transactions = db.temporaryPayment().getLatestPayment()
            _transactionInfo.value = transactions

        }
    }

    fun getHistoryTransactionSubscription() {
        viewModelScope.launch {
            val transactionSubs = ProfileRepository.getHistoryTransactionSubs(db = db)
            _historyTransSubscription.value = transactionSubs
        }
    }

    fun getHistoryTransactionCoin() {
        viewModelScope.launch {
            val transactionSubs = ProfileRepository.getHistoryTransactionCoin(db)
            _historyTransCoin.value = transactionSubs
        }
    }


    fun getHistoryTransactionVideo() {
        viewModelScope.launch {
            val transactionsVideo = ProfileRepository.getHistoryTransactionVideo(db)
            _historyTransVideo.value = transactionsVideo
        }
    }

    fun cancelTransaction(db: AppDatabase,idPayment: String) {
        viewModelScope.launch {
            ProfileRepository.cancelPayment(db = db, idpayment = idPayment)
        }
    }

    fun makePaymentVideo(
        type: String, db: AppDatabase, bank: String, onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            val result = ProfileRepository.makeDataTransactionVideo(type, bank, db)
            if (result != null) {
                _transactionInfo.value = result
                onSuccess()
            } else {
                onError()
            }
        }
    }

    fun topUpSaldoSubscription(
        type: String, db: AppDatabase, level: String, bank: String, onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            val result = ProfileRepository.makeSubscription(level, db, type, bank)
            if (result != null) {
                _transactionInfo.value = result
                onSuccess()
            } else {
                onError()
            }
        }
    }

    fun topUpSaldoKoin(
        type: String, db: AppDatabase, amount: Int, bank: String, onSuccess: () -> Unit,
        onError: () -> Unit
    ) {

        if (type == "va") {
            viewModelScope.launch {
                val result = ProfileRepository.topUpSaldo(type, amount, db, bank)
                if (result != null) {
                    _transactionInfo.value = result
                    println(result)
                    onSuccess()
                } else {
                    onError()
                }
            }
        } else {
            viewModelScope.launch {
                val result = ProfileRepository.topUpSaldoQr(type, amount, db)
                if (result != null) {
                    _transactionInfo.value = result
                    println(result)
                }
            }

        }

    }
}

// Data class untuk kategori dan item pembayaran
data class PaymentCategory(val title: String, val items: List<PaymentItem>, val type: String)
data class PaymentItem(
    val iconRes: Int,
    val name: String,
    val value: String,
    val online: Boolean = false,
    val status: String = "Available"
)

