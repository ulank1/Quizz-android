package kg.mvvmdordoi.ui.main.game

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
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.ui.game.users.Shared
import kg.mvvmdordoi.ui.test.test_detail.GameQuestionActivity
import kg.mvvmdordoi.ui.test.test_detail.GameQuestionActivityInvite
import kg.mvvmdordoi.utils.URL1
import kg.mvvmdordoi.utils.extension.gone
import kg.mvvmdordoi.utils.extension.invisible
import kg.mvvmdordoi.utils.extension.visible


class GameRvAdapter(val context: Context) : RecyclerView.Adapter<GameRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<GameOuter> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_game_invite, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: ArrayList<GameOuter>) {
        this.data = data
        notifyDataSetChanged()
    }


    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: GameOuter) = with(itemView) {

            val name: TextView = itemView.findViewById(R.id.name)
            val text: TextView = itemView.findViewById(R.id.text)
            val pointText: TextView = itemView.findViewById(R.id.point)
            val nameOuter: TextView = itemView.findViewById(R.id.name_outer)
            val image: ImageView = itemView.findViewById(R.id.image)
            val imageOuter: ImageView = itemView.findViewById(R.id.image_outer)
            val winnerOwner: ImageView = itemView.findViewById(R.id.left_win)
            val winnerOuter: ImageView = itemView.findViewById(R.id.right_win)
            val startButton: Button = itemView.findViewById(R.id.start)


            var is_owner = true

            is_owner = UserToken.getToken(context) == item.user_owner.id.toString()

            var point_owner = item.owner_point
            var point_outer = item.outer_point

            if (point_outer > -1) {

                if (point_owner > point_outer) {
                    winnerOwner.visible()
                    winnerOuter.invisible()
                    text.visible()
                    startButton.invisible()
                    if (is_owner) {
                        text.text = "Вы выиграли!"
                    }else{
                        text.text = "Вы проиграли!"
                    }
                } else if(point_owner<point_outer) {
                    winnerOuter.visible()
                    winnerOwner.invisible()
                    text.visible()
                    startButton.invisible()
                    if (!is_owner) {
                        text.text = "Вы выиграли!"
                    }else{
                        text.text = "Вы проиграли!"
                    }
                }else{
                    winnerOwner.gone()
                    winnerOuter.gone()
                    text.text = "Ничья!"
                    text.visible()
                    startButton.invisible()
                }
            }

            else if (point_outer == -1) {
                if (is_owner) {
                    text.text = "Ожидание соперника ..."
                    winnerOwner.gone()
                    winnerOuter.gone()
                    text.visible()
                    startButton.invisible()
                } else {
                    winnerOwner.gone()
                    winnerOuter.gone()
                    startButton.visible()
                    text.gone()
                }
            }

            else if (point_owner == -1) {
                if (is_owner) {
                    winnerOwner.gone()
                    winnerOuter.gone()
                    startButton.visible()
                    text.gone()
                } else {
                    text.text = "Ожидание соперника ..."
                    winnerOwner.gone()
                    winnerOuter.gone()
                    text.visible()
                    startButton.invisible()
                }
            }

            val point = getPoint(point_owner) + " - " + getPoint(point_outer)

            pointText.text = point

            name.text = item.user_owner.name
            nameOuter.text = item.user_outer.name

            Glide.with(context).load(URL1+item.user_owner.avatar)
                .apply(
                    RequestOptions.bitmapTransform(
                        (CropCircleTransformation())
                    )
                ).into(image)
            Glide.with(context).load(URL1+item.user_outer.avatar)
                .apply(
                    RequestOptions.bitmapTransform(
                        (CropCircleTransformation())
                    )
                ).into(imageOuter)
            startButton.setOnClickListener {
                Shared.gameOuter = item
                val intent = Intent(App.activity, GameQuestionActivityInvite::class.java)
                intent.putExtra("title", item.category.toString())
                intent.putExtra("id",item.id)
                App.activity!!.startActivity(intent)
            }

        }
    }

    public fun getPoint(point: Int): String {

        return if (point == -1) {
            "-"
        } else {
            point.toString()
        }

    }

}