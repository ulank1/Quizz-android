package kg.mvvmdordoi.ui.test

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
import kg.mvvmdordoi.model.get.Test
import kg.mvvmdordoi.ui.test.test_detail.QuestionActivity


class TestRvAdapter(val context: Context,val title: String) : RecyclerView.Adapter<TestRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Test> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_test, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: ArrayList<Test>) {
        this.data = data
        notifyDataSetChanged()
    }



    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Test) = with(itemView) {

            val name:TextView = itemView.findViewById(R.id.name)

            name.text = item.name

            name.setOnClickListener {
                val intent = Intent(App.activity,QuestionActivity::class.java)
                intent.putExtra("title",item.name)
                intent.putExtra("title_category",title)
                intent.putExtra("id",item.id)
                intent.putExtra("desc",item.desc)
                App.activity!!.startActivity(intent)
            }

        }
    }
}