package kg.mvvmdordoi.ui.game.category

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import kg.mvvmdordoi.App
import kotlin.collections.ArrayList
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.Game
import kg.mvvmdordoi.ui.game.preview.PreviewActivity
import kg.mvvmdordoi.ui.test.test_detail.GameQuestionActivity
import kg.mvvmdordoi.utils.URL1


class CategotryRvAdapter(val context: Context) : RecyclerView.Adapter<CategotryRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<Game> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_category, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: ArrayList<Game>) {
        this.data = data
        notifyDataSetChanged()
    }



    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Game) = with(itemView) {

            val name:TextView = itemView.findViewById(R.id.name)
            val image:ImageView = itemView.findViewById(R.id.image)

            name.text = item.name

            Log.e("123",item.image)

            Glide.with(context).load(URL1+item.image).into(image)

            itemView.setOnClickListener {
                val intent = Intent(App.activity, GameQuestionActivity::class.java)
                intent.putExtra("title",item.name)
                intent.putExtra("id",item.id)
                App.activity!!.startActivityForResult(intent,101)
            }

        }
    }
}