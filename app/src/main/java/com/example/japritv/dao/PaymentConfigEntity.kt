package com.example.japritv.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.japritv.converters.LimitConverter
import com.example.japritv.converters.PaymentBankConverter
import com.example.japritv.converters.PaymentMethodConverter
import com.example.japritv.converters.PaymentTypeConverter
import com.example.japritv.converters.SubscriptionPriceConverter
import com.example.japritv.converters.SubscriptionValidityConverter
import com.example.japritv.model.BankList
import com.example.japritv.model.Limit
import com.example.japritv.model.PaymentBank
import com.example.japritv.model.SubscriptionPrice
import com.example.japritv.model.SubscriptionValidity


@Entity(tableName = "payment_config")
data class PaymentConfigEntity(
    @PrimaryKey val id: String = "config",

    val subPriceMingguan: Int,
    val subPriceBulanan: Int,
    val subValidityMingguan: Int,
    val subValidityBulanan: Int,
    val paymentTypes: String = "[]",     // JSON string dari List<String>
    val paymentMethods: String = "[]",   // JSON string dari List<String>
    val banks: String = "[]"             // JSON string dari List<BankList>
)

