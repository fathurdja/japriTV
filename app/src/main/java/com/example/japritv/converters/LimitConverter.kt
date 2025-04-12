package com.example.japritv.converters

import androidx.room.TypeConverter
import com.example.japritv.model.Limit
import com.example.japritv.model.PaymentBank
import com.example.japritv.model.SubscriptionPrice
import com.example.japritv.model.SubscriptionValidity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LimitConverter {
    @TypeConverter
    fun fromLimit(limit: Limit): String = Gson().toJson(limit)

    @TypeConverter
    fun toLimit(json: String): Limit = Gson().fromJson(json, Limit::class.java)
}

class SubscriptionPriceConverter {
    @TypeConverter
    fun fromSubscriptionPrice(price: SubscriptionPrice): String = Gson().toJson(price)

    @TypeConverter
    fun toSubscriptionPrice(json: String): SubscriptionPrice = Gson().fromJson(json, SubscriptionPrice::class.java)
}

class SubscriptionValidityConverter {
    @TypeConverter
    fun fromSubscriptionValidity(validity: SubscriptionValidity): String = Gson().toJson(validity)

    @TypeConverter
    fun toSubscriptionValidity(json: String): SubscriptionValidity = Gson().fromJson(json, SubscriptionValidity::class.java)
}

class PaymentTypeConverter {
    @TypeConverter
    fun fromList(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun toList(json: String): List<String> =
        Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
}

class PaymentMethodConverter {
    @TypeConverter
    fun fromList(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun toList(json: String): List<String> =
        Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
}

class PaymentBankConverter {
    @TypeConverter
    fun fromList(list: List<PaymentBank>): String = Gson().toJson(list)

    @TypeConverter
    fun toList(json: String): List<PaymentBank> =
        Gson().fromJson(json, object : TypeToken<List<PaymentBank>>() {}.type)
}
