package kg.mvvmdordoi.ui.main.forum.topic

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kg.mvvmdordoi.App
import kotlin.collections.ArrayList
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.*
import kg.mvvmdordoi.ui.test.test_detail.GameQuestionActivity
import kotlinx.android.synthetic.main.item_forum.view.*
import kotlinx.android.synthetic.main.item_forum.view.title
import kotlinx.android.synthetic.main.item_rating_all.view.*
import kotlinx.android.synthetic.main.item_topic.view.*


class TopicRvAdapter(val context: Context) : RecyclerView.Adapter<TopicRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Topic> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_topic, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position],position)
    fun swapData(data: ArrayList<Topic>) {
        this.data = data
        notifyDataSetChanged()
    }



    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Topic, position: Int) = with(itemView) {

            itemView.title.text = item.title
            itemView.comment_count.text = item.comment_count.toString()

            itemView.setOnClickListener {
//                val intent = Intent(App.activity, GameQuestionActivity::class.java)
//                App.activity!!.startActivity(intent)
            }

        }
    }
}