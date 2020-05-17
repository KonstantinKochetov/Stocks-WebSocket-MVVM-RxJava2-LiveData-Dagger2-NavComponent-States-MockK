package com.kochetov.websocketstockprices.modules.stocks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kochetov.websocketstockprices.R
import com.kochetov.websocketstockprices.common.StocksConstants
import com.kochetov.websocketstockprices.usecases.stocks.model.Stock
import java.math.BigDecimal
import java.text.DecimalFormat
import kotlinx.android.synthetic.main.stock_item.view.*

class StocksAdapter(private var items: List<Stock>) :
    RecyclerView.Adapter<StocksAdapter.ViewHolder>() {

    companion object {
        const val PAYLOAD_UPDATE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            items[position]
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            when (payloads[0]) {
                PAYLOAD_UPDATE -> {
                    if (position != 0) {
                        holder.updateAmount(items[position])
                    }
                }
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(
            stock: Stock
        ) {
            itemView.tv_company_name.text = stock.companyName
            itemView.tv_isin_code.text = stock.isinCode
            setFormattedAmountText(stock)
        }

        fun updateAmount(stock: Stock) {
            setFormattedAmountText(stock)
        }

        private fun setFormattedAmountText(stock: Stock) {
            itemView.tv_currency_amount.text = stock.price.format()
        }
    }

    fun updateStock(stock: Stock) {
        StocksConstants.stocksMap[stock.isinCode]?.let {
            items[it.position].price = stock.price
            notifyItemChanged(
                it.position,
                PAYLOAD_UPDATE
            )
        }
    }
}

private fun BigDecimal.format(): String = DecimalFormat("00.00").format(this)
