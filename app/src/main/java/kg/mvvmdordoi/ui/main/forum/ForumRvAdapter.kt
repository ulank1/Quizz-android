package kg.mvvmdordoi.ui.main.forum

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
import kg.mvvmdordoi.ui.main.forum.topic.TopicActivity
import kg.mvvmdordoi.ui.test.test_detail.GameQuestionActivity
import kotlinx.android.synthetic.main.item_forum.view.*
import kotlinx.android.synthetic.main.item_rating_all.view.*


class ForumRvAdapter(val context: Context) : RecyclerView.Adapter<ForumRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Forum> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_forum, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position],position)
    fun swapData(data: ArrayList<Forum>) {
        this.data = data
        notifyDataSetChanged()
    }



    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Forum, position: Int) = with(itemView) {

            itemView.title.text = item.title
            Glide.with(context).load(item.image).into(itemView.image)
            itemView.setOnClickListener {
                val intent = Intent(App.activity, TopicActivity::class.java)
                intent.putExtra("forum",item.id)
                intent.putExtra("title",item.title)
                App.activity!!.startActivity(intent)
            }

        }
    }
}