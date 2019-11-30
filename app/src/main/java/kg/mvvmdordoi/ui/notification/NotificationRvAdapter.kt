package kg.mvvmdordoi.ui.notification

import android.app.Activity.RESULT_OK
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
import kg.mvvmdordoi.model.get.Notification
import kg.mvvmdordoi.ui.game.preview.PreviewActivity
import kg.mvvmdordoi.ui.info.news.NewsListActivity
import kg.mvvmdordoi.ui.test.test_detail.GameQuestionActivity
import kg.mvvmdordoi.utils.extension.formatDateNotification


class NotificationRvAdapter(val context: Context) : RecyclerView.Adapter<NotificationRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Notification> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_notification, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: ArrayList<Notification>) {
        this.data = data
        notifyDataSetChanged()
    }



    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Notification) = with(itemView) {

            val name:TextView = itemView.findViewById(R.id.name)
            val desc:TextView = itemView.findViewById(R.id.desc)
            val time:TextView = itemView.findViewById(R.id.time)

            name.text = item.title
            desc.text = item.body
            if (item.created_at!=null)
            time.text = formatDateNotification(item.created_at.toString())

            itemView.setOnClickListener {
                if (item.title.contains("Дуэль")){
                    App.activity!!.setResult(RESULT_OK)
                    App.activity!!.finish()
                }else {
                    val intent = Intent(App.activity, NewsListActivity::class.java)
                    App.activity!!.startActivityForResult(intent, 101)
                }
            }

        }
    }
}