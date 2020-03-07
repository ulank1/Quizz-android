package kg.mvvmdordoi.ui.main.rating_all

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
import kg.mvvmdordoi.model.get.Game
import kg.mvvmdordoi.model.get.GameOuter
import kg.mvvmdordoi.model.get.Rating
import kg.mvvmdordoi.model.get.RatingWithUser
import kg.mvvmdordoi.ui.main.profile.ProfileActivity
import kg.mvvmdordoi.ui.test.test_detail.GameQuestionActivity
import kotlinx.android.synthetic.main.item_rating_all.view.*


class RatingRvAdapter(val context: Context) : RecyclerView.Adapter<RatingRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<RatingWithUser> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_rating_all, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position],position)
    public var size:Int = 0
    fun swapData(data: ArrayList<RatingWithUser>,size:Int) {
        this.data.addAll(data)
        this.size = size
        notifyDataSetChanged()
    }



    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: RatingWithUser, position: Int) = with(itemView) {

            val name:TextView = itemView.findViewById(R.id.name)
            val rating:TextView = itemView.findViewById(R.id.rating)
            val point:TextView = itemView.findViewById(R.id.point)

            name.text = item.user.name
            point.text = item.rating.toString()

            rating.text = "${position+1}"
            name.setOnClickListener {

                context.startActivity(Intent(context, ProfileActivity::class.java).putExtra("id",item.user.id.toString()))

            }


            /* itemView.setOnClickListener {
                 val intent = Intent(App.activity, GameQuestionActivity::class.java)
 //                i ntent.putExtra("title",item.name)
 //                intent.putExtra("id",item.id)
                 App.activity!!.startActivity(intent)
             }*/

        }
    }
}