package kg.mvvmdordoi.ui.ort

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.DialogTitle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kg.mvvmdordoi.App
import kotlin.collections.ArrayList
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.OrtTest
import kg.mvvmdordoi.model.get.Test
import kg.mvvmdordoi.ui.test.test_detail.QuestionActivity
import kg.mvvmdordoi.utils.extension.visible


class OrtTestRvAdapter(val context: Context) :
    RecyclerView.Adapter<OrtTestRvAdapter.AdvertViewHolder>() {

    private var data: ArrayList<OrtTest> = ArrayList()
    private var isActive = false
    private var payID = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_test, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: ArrayList<OrtTest>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun hasActive(isActive: Boolean, id: Int) {
        this.isActive = isActive
        payID = id
    }


    inner class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: OrtTest) = with(itemView) {

            val name: TextView = itemView.findViewById(R.id.name)
            val status: TextView = itemView.findViewById(R.id.status)
            status.visible()
            name.text = item.name

            if(item.status==0){
                status.text = "Не пройден"
                status.setTextColor(Color.GREEN)
            }else{
                status.text = "Пройден"
                status.setTextColor(Color.RED)
            }

            name.setOnClickListener {
                if (isActive) {

                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Вы готовы начать "+name.text+"?")
                    //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                    builder.setPositiveButton("yes") { dialog, which ->
                        Ort.category = item.id
                        Ort.typeOfTest = -1
                        Ort.text1 = item.text1
                        Ort.text2 = item.text2
                        Ort.text3 = item.text3
                        context.startActivity(
                            Intent(
                                context,
                                InfoOrtActivity::class.java
                            ).putExtra("payID", payID)
                        )
                        dialog.cancel()
                    }

                    builder.setNegativeButton("no") { dialog, which ->
                    }

                    builder.show()

                }
            }

        }
    }

}