package com.kochetov.websocketstockprices.usecases.stocks.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

data class Stock(
    @SerializedName("isin") var isinCode: String,
    @SerializedName("price") var price: BigDecimal,
    var companyName: String? = null,
    val position: Int = 0
) : Serializable
