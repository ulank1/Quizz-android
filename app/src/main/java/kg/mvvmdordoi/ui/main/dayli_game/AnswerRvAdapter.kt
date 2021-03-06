package kg.mvvmdordoi.ui.main.dayli_game

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlin.collections.ArrayList
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.*
import kg.mvvmdordoi.ui.main.profile.ProfileActivity
import kg.mvvmdordoi.utils.extension.*
import kotlinx.android.synthetic.main.item_answer.view.*
import kotlinx.android.synthetic.main.item_answer.view.admin
import kotlinx.android.synthetic.main.item_answer.view.time
import kotlinx.android.synthetic.main.item_comment.view.*


class AnswerRvAdapter(
    val context: Context,
    val listener: CommentClickListener,
    val comment_id: Int
) : RecyclerView.Adapter<AnswerRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Comment> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_answer, parent, false)
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
            name.text = item.user.name
            message.text = item.name+" "+item.message
            itemView.time.text = formatDateForum(item.created_at)

            setLike(item,itemView)

            name.setOnClickListener {

                context.startActivity(Intent(context, ProfileActivity::class.java).putExtra("id",item.user.id.toString()))

            }

            if (!item.user.is_admin){
                itemView.admin.invisible()
            }else{
                itemView.admin.visible()
            }

            img_like.setOnClickListener {

                listener.likeAnswerClick(item.id,1)
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

                listener.likeAnswerClick(item.id,2)
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

            itemView.setOnClickListener {

                listener.commentClick(item.user.name,comment_id)

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