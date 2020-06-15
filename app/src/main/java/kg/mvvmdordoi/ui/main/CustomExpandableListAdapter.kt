package kg.mvvmdordoi.ui.main

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kg.mvvmdordoi.R
import kg.mvvmdordoi.model.get.Category
import kg.mvvmdordoi.utils.extension.gone
import kg.mvvmdordoi.utils.extension.visible

import java.util.HashMap
 
class CustomExpandableListAdapter internal constructor(private val context: Context, private val titleList: List<Category>, private val dataList: HashMap<String, List<Category>>) : BaseExpandableListAdapter() {
 
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.dataList[this.titleList[listPosition].name]!![expandedListPosition].name
    }
 
    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }
 
    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.expandedListItem)
        expandedListTextView.text = expandedListText
        return convertView
    }
 
    override fun getChildrenCount(listPosition: Int): Int {
        if(listPosition>=titleList.size-6)
            return 0
        return this.dataList[this.titleList[listPosition].name]!!.size
    }
 
    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition].name
    }
 
    override fun getGroupCount(): Int {
        return this.titleList.size
    }
 
    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }
 
    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listTitle = titleList[listPosition]
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_group, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.listTitle)
        val text_new = convertView!!.findViewById<TextView>(R.id.text_new)
        val image = convertView!!.findViewById<ImageView>(R.id.image)
        Log.e("saasasas12345",listPosition.toString()+" "+listTitle.res.toString()+" "+listTitle.image.toString())
        if(listPosition==titleList.size-6){
            text_new.visible()
        }else{
            text_new.gone()
        }

        if(listPosition>=titleList.size-6){
            image.setImageResource(listTitle.res!!)
        }else{
            Glide.with(context).load(listTitle.image).into(image)
        }

        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle.name
        return convertView
    }
 
    override fun hasStableIds(): Boolean {
        return false
    }
 
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

    public fun getChildId(listPosition:Int,expandedListPosition:Int,type:Int):Int{

        return if (this.dataList[this.titleList[listPosition].name]!![expandedListPosition].name=="Инфо"){
            this.dataList[this.titleList[listPosition].name]!![expandedListPosition].main_category*-1
        }else{
            this.dataList[this.titleList[listPosition].name]!![expandedListPosition].id
        }


    }

}
