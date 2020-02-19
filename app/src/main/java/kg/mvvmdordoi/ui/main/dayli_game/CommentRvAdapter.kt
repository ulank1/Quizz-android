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
import kg.mvvmdordoi.utils.extension.formatDateNotification
import kg.mvvmdordoi.utils.extension.formatDateNotification1
import kg.mvvmdordoi.utils.extension.gone
import kg.mvvmdordoi.utils.extension.visible


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
            val time:TextView = itemView.findViewById(R.id.time)
            val like:TextView = itemView.findViewById(R.id.like_text)
            val un_like:TextView = itemView.findViewById(R.id.un_like_text)
            val img_un_like:ImageView = itemView.findViewById(R.id.img_un_like)
            val img_like:ImageView = itemView.findViewById(R.id.img_like)
            val rv:RecyclerView = itemView.findViewById(R.id.rv)
            setLike(item,itemView)

            name.text = item.user.name
            message.text = item.message
            time.text = formatDateNotification1(item.created_at)


            img_like.setOnClickListener {

                listener.likeClick(item.id,1)
                when {
                    item.is_my_like==2 -> {
                        item.like_count++
                        item.un_like_count--
                        item.is_my_like = 1
                    }
                    item.is_my_like==1 -> {
                        item.like_count--
                        item.is_my_like = 0
                    }
                    else -> {
                        item.like_count++
                        item.is_my_like = 1
                    }
                }
                setLike(item,itemView)
            }
            img_un_like.setOnClickListener {

                listener.likeClick(item.id,2)
                if (item.is_my_like==2){
                    item.un_like_count = item.un_like_count-1
                    item.is_my_like = 0
                }else if (item.is_my_like==1){
                    item.un_like_count++
                    item.like_count--
                    item.is_my_like = 2
                }else{
                    item.un_like_count++
                    item.is_my_like = 2
                }
                setLike(item,itemView)
            }

            if (!item.answers.isNullOrEmpty()){
                rv.visible()
                val layoutManager = GridLayoutManager(context, 1)
                rv.layoutManager = layoutManager
                var adapter = AnswerRvAdapter(App.activity!!,listener,item.id)
                rv.adapter = adapter
                adapter.swapData(item.answers!!)
            }else{
                rv.gone()
            }

            itemView.setOnClickListener {
               listener.commentClick(item.user.name,item.id)
            }
        }
    }

    fun setLike(item:Comment,itemView: View){

        val like:TextView = itemView.findViewById(R.id.like_text)
        val un_like:TextView = itemView.findViewById(R.id.un_like_text)
        val img_un_like:ImageView = itemView.findViewById(R.id.img_un_like)
        val img_like:ImageView = itemView.findViewById(R.id.img_like)

        img_like.setImageResource(R.drawable.ic_like_non)
        img_un_like.setImageResource(R.drawable.ic_un_like_none)

        if (item.is_my_like==1){
            img_like.setImageResource(R.drawable.ic_like)
        }else if(item.is_my_like==2){
            img_un_like.setImageResource(R.drawable.ic_un_like)
        }
        like.text = item.like_count.toString()
        un_like.text = item.un_like_count.toString()
    }
}