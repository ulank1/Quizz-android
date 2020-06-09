package kg.mvvmdordoi.ui.ort

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.DialogTitle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kg.mvvmdordoi.App
import kotlin.collections.ArrayList
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.HistoryOrt
import kg.mvvmdordoi.model.get.OrtTest
import kg.mvvmdordoi.model.get.Test
import kg.mvvmdordoi.ui.test.test_detail.QuestionActivity
import kotlinx.android.synthetic.main.item_history_ort.view.*


class OrtHistoryRvAdapter(val context: Context) :
    RecyclerView.Adapter<OrtHistoryRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<HistoryOrt> = ArrayList()
    private var isActive = false
    private var payID = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_ort, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: ArrayList<HistoryOrt>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun hasActive(isActive: Boolean, id: Int) {
        this.isActive = isActive
        payID = id
    }


    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: HistoryOrt) = with(itemView) {
            true1.text = "${item.math1}/30"
            true2.text = "${item.math2}/30"
            true3.text = "${item.analog}/30"
            true4.text = "${item.understand}/30"
            true5.text = "${item.grammar}/30"
            result.text = item.point.toString()
        }
    }

}