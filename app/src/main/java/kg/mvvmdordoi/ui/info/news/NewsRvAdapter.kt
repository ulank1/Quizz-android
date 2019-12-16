package kg.mvvmdordoi.ui.info.news

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.*
import com.bumptech.glide.Glide
import kg.mvvmdordoi.App
import kotlin.collections.ArrayList
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.Game
import kg.mvvmdordoi.model.get.Info
import kg.mvvmdordoi.model.get.University
import kg.mvvmdordoi.ui.test.test_detail.GameQuestionActivity
import kg.mvvmdordoi.utils.URL1
import kotlinx.android.synthetic.main.activity_question.*


class NewsRvAdapter(val context: Context) : RecyclerView.Adapter<NewsRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Info> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_news, parent, false)
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
            val desc:TextView= itemView.findViewById(R.id.desc)

            name.text = item.name
//            desc.loadData(
//                "<html><body>" + correctImage(item.desc) + "</body></html>",
//                "text/html; charset=utf-8",
//                "UTF-8"
//            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                desc.text = Html.fromHtml(item.desc, Html.FROM_HTML_MODE_COMPACT)
            } else {
                desc.text = Html.fromHtml(item.desc)
            }


            name.setOnClickListener {
                val intent = Intent(App.activity, NewsInfoActivity::class.java)
                intent.putExtra("info",item)
                App.activity!!.startActivity(intent)
            }

        }
    }

    private fun correctImage(data: String): String {

        var true_data = data.replace(
            "/media/django-summernote",
            "$URL1/media/django-summernote"
        )

        Log.e("TRUEDATA", true_data)

        return true_data
    }
}