package kg.mvvmdordoi.utils
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kg.mvvmdordoi.R
import kotlinx.android.synthetic.main.activity_image.*



class ImageActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        Glide.with(this).load(intent.getStringExtra("image")).into(myZoomageView)

        back.setOnClickListener { finish() }
    }
}
