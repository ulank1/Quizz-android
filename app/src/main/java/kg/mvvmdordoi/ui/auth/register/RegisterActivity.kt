package kg.mvvmdordoi.ui.auth.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.DatePicker
import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.utils.ImagePickerHelper
import kg.mvvmdordoi.utils.extension.toast
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.kg
import kotlinx.android.synthetic.main.activity_register.ru
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RegisterActivity : ImagePickerHelper(),DatePickerDialog.OnDateSetListener {
    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        birth_date.setText("$year-${month+1}-$dayOfMonth")
    }

    override fun setImagePath(imgpath: Uri) {

        imagePath = imgpath.path
        avatar.setImageURI(imgpath)
    }

    override fun openGallery(mItemId: String) {

    }
     var imagePath:String?= null

    private lateinit var viewModel: RegisterViewModel
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        birth_date.setOnTouchListener { v, event ->

            if (event.action == MotionEvent.ACTION_UP){
                val date = DatePickerDialog(this,this,1999,1,1)
                date.datePicker.touchables[0].performClick()
                date.show()
            }

            return@setOnTouchListener true
        }

        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.regisre)

        App.activity = this
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(RegisterViewModel::class.java)

        viewModel.isExist.observe(this, Observer {
            Log.e("Boolean",it.toString())
            if (it != null) {
                if (it) {
                    register()
                }else{
                    toast(getString(R.string.account_registered))
                }
            }
        })

        setOnClickLiteners()
    }

    private fun register() {

        var regions = resources.getStringArray(R.array.regions)


        val login = RequestBody.create(MediaType.parse("text/plain"), login.text.toString())
        val is_kg = RequestBody.create(MediaType.parse("text/plain"), kg.isChecked.toString())
        val is_ru = RequestBody.create(MediaType.parse("text/plain"), ru.isChecked.toString())
        val name = RequestBody.create(MediaType.parse("text/plain"), name.text.toString())
        val password = RequestBody.create(MediaType.parse("text/plain"), password.text.toString())
        val birthDate = RequestBody.create(MediaType.parse("text/plain"), birth_date.text.toString())
        val region = RequestBody.create(MediaType.parse("text/plain"), regions[spinner.selectedItemPosition])

        if (imagePath!=null) {
            var imageFile = File(imagePath)
            val file = RequestBody.create(MediaType.parse("image/*"), imageFile)
            val fileToUpload = MultipartBody.Part.createFormData("avatar", imageFile!!.name, file)
            viewModel.register(login, password, name, fileToUpload, birthDate, region, is_kg, is_ru)
        }else{
            viewModel.register(login, password, name, birthDate, region, is_kg, is_ru)

        }
    }

    private fun setOnClickLiteners() {
        btn_sign_up.setOnClickListener {
            if (isValidate()) {
                viewModel.isExistLogin(login.text.toString(),name.text.toString())
            }
        }

        avatar.setOnClickListener {

            showPickImageDialog()

        }
    }

    private fun isValidate(): Boolean {
        var boolean = true

        if (login.text.toString().length < 10) {
            boolean = false
            login.error = getString(R.string.phone_incorrect)
        }

        if (password.text.toString().isEmpty()) {
            boolean = false
            password.error = getString(R.string.empty_password)
        }

        if (password.text.toString() != repeat_password.text.toString()) {
            boolean = false
            repeat_password.error = getString(R.string.no_password)
        }

        if (birth_date.text.toString().isEmpty()){
            boolean = false
            birth_date.error = getString(R.string.date_incorrect)
        }

        return boolean
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home-> finish()
        }

        return true
    }

}
