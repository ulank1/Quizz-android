package kg.mvvmdordoi.ui.game.preview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kg.mvvmdordoi.R
import kg.mvvmdordoi.ui.game.users.Shared
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.activity_question_owner.*
import kotlinx.android.synthetic.main.activity_question_owner.image1
import kotlinx.android.synthetic.main.activity_question_owner.image2
import kotlinx.android.synthetic.main.activity_question_owner.name_outer

class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        name_outer.text = Shared.name_outer
        Glide.with(this).load(kg.mvvmdordoi.ui.test.test_detail.Shared.user.avatar).into(image1)
        Glide.with(this).load(Shared.avatar_outer).into(image2)


    }
}
