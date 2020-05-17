package com.kochetov.websocketstockprices.common

import com.kochetov.websocketstockprices.usecases.stocks.model.Stock
import java.math.BigDecimal

class StocksConstants {
    companion object {
        val stocksMap = mutableMapOf(
            "US0378331005" to Stock(
                isinCode = "US0378331005",
                price = BigDecimal(0),
                companyName = "Apple",
                position = 0
            ),
            "XS2156107646" to Stock(
                isinCode = "XS2156107646",
                price = BigDecimal(0),
                companyName = "Goldman Sachs International",
                position = 1
            ),
            "XS2178221730" to Stock(
                isinCode = "XS2178221730",
                price = BigDecimal(0),
                companyName = "HSBC Bank plc",
                position = 2
            ),
            "XS2178486952" to Stock(
                isinCode = "XS2178486952",
                price = BigDecimal(0),
                companyName = "Managed and Enhanced Tap Funding ST",
                position = 3
            ),
            "XS2178497728" to Stock(
                isinCode = "XS2178497728",
                price = BigDecimal(0),
                companyName = "Mitsubishi UFJ Trust",
                position = 4
            ),
            "XS2178562307" to Stock(
                isinCode = "XS2178562307",
                price = BigDecimal(0),
                companyName = "Qatar National Bank QPSC[London]",
                position = 5
            ),
            "DE000DK0UD66" to Stock(
                isinCode = "DE000DK0UD66",
                price = BigDecimal(0),
                companyName = "UBS AG [London]",
                position = 6
            ),
            "XS2176795677" to Stock(
                isinCode = "XS2176795677",
                price = BigDecimal(0),
                companyName = "Barclays Plc",
                position = 7
            ),
            "XS2112183129" to Stock(
                isinCode = "XS2112183129",
                price = BigDecimal(0),
                companyName = "Credit Suisse International [Milan]",
                position = 8
            ),
            "XS2095611153" to Stock(
                isinCode = "XS2095611153",
                price = BigDecimal(0),
                companyName = "BNP Paribas SA [Hong Kong]",
                position = 9
            )
        )
    }
}