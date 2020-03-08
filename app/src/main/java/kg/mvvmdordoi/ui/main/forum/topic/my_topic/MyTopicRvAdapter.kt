package kg.mvvmdordoi.ui.main.forum.topic.my_topic

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import kg.mvvmdordoi.ui.main.forum.comment_forum.CommentForumActivity
import kg.mvvmdordoi.ui.test.test_detail.GameQuestionActivity
import kotlinx.android.synthetic.main.item_forum.view.*
import kotlinx.android.synthetic.main.item_forum.view.title
import kotlinx.android.synthetic.main.item_my_topic.view.*
import kotlinx.android.synthetic.main.item_rating_all.view.*
import kotlinx.android.synthetic.main.item_topic.view.*
import kotlinx.android.synthetic.main.item_topic.view.comment_count


class MyTopicRvAdapter(val context: Context) : RecyclerView.Adapter<MyTopicRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Topic> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_my_topic, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position],position)
    fun swapData(data: ArrayList<Topic>) {
        this.data = data
        notifyDataSetChanged()
    }

    interface OnClickItem{

        fun onClickItemDelete(id:Int)

    }

    var listener: OnClickItem? = null

    fun setListener1(listener:OnClickItem){
        this.listener = listener
    }

    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Topic, position: Int) = with(itemView) {

            itemView.title.text = item.title
            itemView.comment_count.text = item.comment_count.toString()

            itemView.delete.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(context.getString(R.string.delete))
                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                builder.setPositiveButton("yes") { dialog, which ->
                    listener?.onClickItemDelete(item.id)
                }

                builder.setNegativeButton("no") { dialog, which ->
                }


                builder.show()
            }

            itemView.setOnClickListener {
                val intent = Intent(App.activity, CommentForumActivity::class.java)
                intent.putExtra("topic",item.id)
                intent.putExtra("topic1",item)
                intent.putExtra("title",item.title)
                App.activity!!.startActivity(intent)
            }

        }
    }
}