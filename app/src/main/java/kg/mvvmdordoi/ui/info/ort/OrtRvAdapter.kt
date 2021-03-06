package kg.mvvmdordoi.ui.info.ort

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import kg.mvvmdordoi.App
import kotlin.collections.ArrayList
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.Game
import kg.mvvmdordoi.model.get.Info
import kg.mvvmdordoi.model.get.University
import kg.mvvmdordoi.ui.test.test_detail.GameQuestionActivity


class OrtRvAdapter(val context: Context) : RecyclerView.Adapter<OrtRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Info> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_info, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: ArrayList<Info>) {
        this.data = data
        notifyDataSetChanged()
    }



    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Info) = with(itemView) {

            val name:TextView = itemView.findViewById(R.id.name)

            name.text = item.name


            name.setOnClickListener {
                val intent = Intent(App.activity, OrtInfoActivity::class.java)
                intent.putExtra("info",item)
                App.activity!!.startActivity(intent)
            }

        }
    }
}