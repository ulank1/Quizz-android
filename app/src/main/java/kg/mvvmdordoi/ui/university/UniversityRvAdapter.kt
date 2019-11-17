package kg.mvvmdordoi.ui.university

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


class UniversityRvAdapter(val context: Context) : RecyclerView.Adapter<UniversityRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<University> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_university, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: ArrayList<University>) {
        this.data = data
        notifyDataSetChanged()
    }



    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: University) = with(itemView) {

            val name:TextView = itemView.findViewById(R.id.name)
            val image:ImageView = itemView.findViewById(R.id.image)

            name.text = item.name
            Glide.with(context).load(item.avatar).into(image)

            name.setOnClickListener {
                val intent = Intent(App.activity, UniversityInfoActivity::class.java)
                intent.putExtra("info",item)
                App.activity!!.startActivity(intent)
            }

        }
    }
}