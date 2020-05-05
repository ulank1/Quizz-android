package kg.mvvmdordoi.ui.ort

import android.content.Context
import android.content.Intent
import android.support.v7.widget.DialogTitle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kg.mvvmdordoi.App
import kotlin.collections.ArrayList
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.OrtTest
import kg.mvvmdordoi.model.get.Test
import kg.mvvmdordoi.ui.test.test_detail.QuestionActivity


class OrtTestRvAdapter(val context: Context) : RecyclerView.Adapter<OrtTestRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<OrtTest> = ArrayList()
    private var isActive = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_test, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: ArrayList<OrtTest>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun hasActive(isActive:Boolean) {
        this.isActive = isActive
    }



    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: OrtTest) = with(itemView) {

            val name:TextView = itemView.findViewById(R.id.name)

            name.text = item.name

            name.setOnClickListener {
                Ort.typeOfTest = -1
                context.startActivity(Intent(context,InfoOrtActivity::class.java))
            }

        }
    }
}