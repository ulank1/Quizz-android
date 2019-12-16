package kg.mvvmdordoi.utils.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.support.annotation.StringRes
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun View.getParentActivity(): AppCompatActivity?{
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun EditText.validate(
    validator: (String) -> Boolean,
    message: String
): Boolean {
    val flag = validator(this.text.toString())
    this.error = if (flag) null else message

    return flag
}
fun TextInputEditText.validate(
    validator: (String) -> Boolean,
    message: String
): Boolean {
    val flag = validator(this.text.toString())
    this.error = if (flag) null else message

    return flag
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG): Toast =
    Toast.makeText(this, message, duration).apply {
        show()
    }

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_LONG): Toast =
    Toast.makeText(this, resId, duration).apply {
        show()
    }

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}
fun View.invisible() {
    visibility = View.INVISIBLE
}

@SuppressLint("SimpleDateFormat")
fun getTodayDate():String{
    val df = SimpleDateFormat("yyyy/MM/dd")
    return df.format(Date())
}

@SuppressLint("SimpleDateFormat")
fun getTodayDateDot():String{
    val df = SimpleDateFormat("dd.MM.yyyy")
    return df.format(Date())
}

fun Activity.hideKeyboard(){
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

@SuppressLint("SimpleDateFormat")
fun formatDateNotification(date:String):String{

    var date1 = date.substring(0,date.indexOf("."))
    Log.e("DATEEEE",date1)
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    var date2 = sdf.parse(date1)
    val sdf1 = SimpleDateFormat("yyyy-MM-dd HH:mm")

    return sdf1.format(date2)
}

@SuppressLint("SimpleDateFormat")
fun getDateDot(num:Int,typeOfClendar:Int):String{
    val df = SimpleDateFormat("dd.MM.yyyy")

    var calendar = Calendar.getInstance()
    calendar.add(typeOfClendar,num)
    var date:Date = calendar.time
    return df.format(date)
}

@SuppressLint("SimpleDateFormat")
fun getDateDotDate(num:Int,typeOfClendar:Int,create_at: String):String{
    val df = SimpleDateFormat("dd.MM.yyyy")
    var date1 = df.parse(create_at)
    var calendar = Calendar.getInstance()
    calendar.time = date1
    calendar.add(typeOfClendar,num)
    var date:Date = calendar.time
    return df.format(date)
}