package kg.mvvmdordoi.ui.main.dayli_game

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kg.mvvmdordoi.App
import kotlin.collections.ArrayList
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.*


class CommentRvAdapter(val context: Context,val listener: CommentClickListener) : RecyclerView.Adapter<CommentRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Comment> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_comment, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position],position)
    fun swapData(data: ArrayList<Comment>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Comment, position: Int) = with(itemView) {

            val name:TextView = itemView.findViewById(R.id.name)
            val message:TextView = itemView.findViewById(R.id.message)
            val like:TextView = itemView.findViewById(R.id.like_text)
            val un_like:TextView = itemView.findViewById(R.id.un_like_text)
            val img_un_like:ImageView = itemView.findViewById(R.id.img_un_like)
            val img_like:ImageView = itemView.findViewById(R.id.img_like)
            val rv:RecyclerView = itemView.findViewById(R.id.rv)

            name.text = item.user.name
            message.text = item.message
            img_like.setImageResource(R.drawable.ic_like_non)
            img_un_like.setImageResource(R.drawable.ic_un_like_none)

            if (item.is_my_like==1){
                img_like.setImageResource(R.drawable.ic_like)
            }else if(item.is_my_like==2){
                img_un_like.setImageResource(R.drawable.ic_un_like)

            }
            like.text = item.like_count.toString()
            un_like.text = item.un_like_count.toString()

            if (!item.answers.isNullOrEmpty()){
                val layoutManager = GridLayoutManager(context, 1)
                rv.layoutManager = layoutManager
                var adapter = AnswerRvAdapter(App.activity!!,listener,item.id)
                rv.adapter = adapter
                adapter.swapData(item.answers!!)
            }

            itemView.setOnClickListener {
               listener.commentClick(item.user.name,item.id)
            }
        }
    }
}