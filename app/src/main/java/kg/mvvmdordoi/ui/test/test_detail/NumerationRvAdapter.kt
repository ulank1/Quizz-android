package kg.mvvmdordoi.ui.test.test_detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kg.mvvmdordoi.App
import kotlin.collections.ArrayList
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.Quiz
import kg.mvvmdordoi.model.get.Test
import kg.mvvmdordoi.ui.test.test_detail.QuestionActivity


class NumerationRvAdapter(val context: Context,val listener: NumerationListener) : RecyclerView.Adapter<NumerationRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Quiz> = ArrayList()
    private var choosePosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_numeration, parent, false)
        )
    }

    fun setChoosePosition(position: Int){
        choosePosition = position
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position],position)

    fun swapData(data: ArrayList<Quiz>) {
        this.data = data
        notifyDataSetChanged()
    }



    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Quiz,position: Int) = with(itemView) {

            val name:TextView = itemView.findViewById(R.id.name)

            name.text = (position+1).toString()


            if (item.choosenPosition!=null){
                if (item.choosenPosition==item.true_answer){
                    name.setBackgroundResource(R.drawable.blue_stroke_1dp_circle)
                }else{
                    name.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
                }
            }else{
                name.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
            }

            name.setOnClickListener {
               /* setChoosePosition(position)
                listener.clickNumeration(position)*/
            }

        }
    }
}