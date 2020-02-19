package kg.mvvmdordoi.ui.game.users

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
import kg.mvvmdordoi.model.get.User
import kg.mvvmdordoi.ui.game.category.CategoryActivity
import kg.mvvmdordoi.ui.test.test_detail.QuestionActivity
import kotlinx.android.synthetic.main.item_product.view.*


class UserRvAdapter(val context: Context,val listener: FriendListener) : RecyclerView.Adapter<UserRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: ArrayList<User>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData(){
        data.clear()
    }

    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: User) = with(itemView) {

            val name:TextView = itemView.findViewById(R.id.name)
            val image:ImageView = itemView.findViewById(R.id.image)
            val friend:ImageView = itemView.findViewById(R.id.add_friend)

            var text = item.name+" ["

            if (item.is_kg!=null){
                if (item.is_kg){
                    text+="kg/"
                }
            }
            if (item.is_ru!=null){
                if (item.is_ru){
                    text+="ru/"
                }
            }

            if (item.friend){
                friend.setImageResource(R.drawable.ic_add_friend)
            }else{
                friend.setImageResource(R.drawable.ic_add_friend_non)
            }

            friend.setOnClickListener {
                var title = if (item.friend){
                    context.getString(R.string.delete_friend)
                }else{
                    context.getString(R.string.add_friend)
                }
                val builder = AlertDialog.Builder(context)
                builder.setTitle(title)
                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                builder.setPositiveButton("yes") { dialog, which ->
                    if (item.friend){
                        friend.setImageResource(R.drawable.ic_add_friend_non)
                    }else{
                        friend.setImageResource(R.drawable.ic_add_friend)
                    }
                    item.friend = !item.friend
                    listener.onClickAddFriend(item.id)

                }

                builder.setNegativeButton("no") { dialog, which ->
                }


                builder.show()

            }

            text = text.substring(0,text.length-1)

            text+="]"


            name.text = text

            Glide.with(context).load(item.avatar)
                .apply(
                    RequestOptions.bitmapTransform(
                        (CropCircleTransformation())
                    )
                ).into(image)



            itemView.setOnClickListener {
                Shared.id = item.id
                Shared.name_outer = item.name
                Shared.avatar_outer = item.avatar
                val intent = Intent(App.activity,CategoryActivity::class.java)
                intent.putExtra("id",item.id)
                App.activity!!.startActivityForResult(intent,101)
            }

        }
    }
}