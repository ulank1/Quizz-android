package kg.mvvmdordoi.ui.test.test_detail

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import kg.mvvmdordoi.App
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Quiz
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.widget.TextView
import kg.mvvmdordoi.R
import kg.mvvmdordoi.utils.extension.gone
import kg.mvvmdordoi.utils.extension.visible
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.webkit.WebView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.ui.game.users.Shared
import kg.mvvmdordoi.utils.URL1
import kg.mvvmdordoi.utils.extension.toast
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_question_owner.*
import kotlinx.android.synthetic.main.activity_question_owner.a
import kotlinx.android.synthetic.main.activity_question_owner.b
import kotlinx.android.synthetic.main.activity_question_owner.back
import kotlinx.android.synthetic.main.activity_question_owner.btn_start
import kotlinx.android.synthetic.main.activity_question_owner.c
import kotlinx.android.synthetic.main.activity_question_owner.choose_a
import kotlinx.android.synthetic.main.activity_question_owner.choose_b
import kotlinx.android.synthetic.main.activity_question_owner.choose_c
import kotlinx.android.synthetic.main.activity_question_owner.choose_d
import kotlinx.android.synthetic.main.activity_question_owner.choose_e
import kotlinx.android.synthetic.main.activity_question_owner.d
import kotlinx.android.synthetic.main.activity_question_owner.e
import kotlinx.android.synthetic.main.activity_question_owner.image1
import kotlinx.android.synthetic.main.activity_question_owner.image11
import kotlinx.android.synthetic.main.activity_question_owner.image2
import kotlinx.android.synthetic.main.activity_question_owner.image21
import kotlinx.android.synthetic.main.activity_question_owner.line_b
import kotlinx.android.synthetic.main.activity_question_owner.line_c
import kotlinx.android.synthetic.main.activity_question_owner.line_d
import kotlinx.android.synthetic.main.activity_question_owner.line_e
import kotlinx.android.synthetic.main.activity_question_owner.line_preview
import kotlinx.android.synthetic.main.activity_question_owner.line_quiz
import kotlinx.android.synthetic.main.activity_question_owner.line_result
import kotlinx.android.synthetic.main.activity_question_owner.name_outer
import kotlinx.android.synthetic.main.activity_question_owner.name_outer1
import kotlinx.android.synthetic.main.activity_question_owner.next
import kotlinx.android.synthetic.main.activity_question_owner.ok
import kotlinx.android.synthetic.main.activity_question_owner.point
import kotlinx.android.synthetic.main.activity_question_owner.question
import kotlinx.android.synthetic.main.activity_question_owner.rv
import kotlinx.android.synthetic.main.activity_question_owner.test_number
import kotlinx.android.synthetic.main.activity_question_owner.time
import kotlinx.android.synthetic.main.activity_question_owner.toolbar
import kotlinx.android.synthetic.main.activity_question_owner_invite.*
import java.lang.Exception


class GameQuestionActivity : AppCompatActivity(), NumerationListener, View.OnClickListener {

    private var sum = 0
    override fun clickNumeration(position: Int) {
        currentPosition = position
        setQuestion()
    }

    var currentPosition = 0
    private lateinit var quizzes: ArrayList<Quiz>
    private lateinit var viewModel: QuestionViewModel
    lateinit var adapter: NumerationRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_owner)
        setAd()
        setSupportActionBar(toolbar)

        var title = "Тест"
        title = intent.getStringExtra("title")
        supportActionBar!!.title = title.toString()
        back.setOnClickListener { finish() }

        App.activity = this
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(QuestionViewModel::class.java)

        setSSS()

        setEditWeb()

        setupRv()

        viewModel.tests.observe(this, Observer {
            getTime(it as ArrayList<Quiz>)

            quizzes = it
            currentPosition = 0
            setQuestion()
        })



        setOnclickListeners()
    }

    private lateinit var mInterstitialAd: InterstitialAd

    fun setAd(){
        MobileAds.initialize(
            this,
            getString(R.string.app_id_ad_mob)
        )
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.ad_video_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.e("Status", "onAdLoaded")
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Log.e("Status", "onAdFailedToLoad")
                toast(errorCode.toString())
            }

            override fun onAdOpened() {
                Log.e("Status", "onAdOpened")
            }

            override fun onAdClicked() {
                Log.e("Status", "onAdClicked")
            }

            override fun onAdLeftApplication() {
                Log.e("Status", "onAdLeftApplication")
            }

            override fun onAdClosed() {
                Log.e("Status", "onAdClosed")
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }
    }



    private fun setSSS() {
        name_outer1.text = Shared.name_outer
        if (kg.mvvmdordoi.ui.test.test_detail.Shared.user!=null) {
            Glide.with(this).load(kg.mvvmdordoi.ui.test.test_detail.Shared.user).into(image11)
        }
        if (Shared.avatar_outer!=null) {
            Glide.with(this).load(Shared.avatar_outer).into(image21)
        }
        btn_start.setOnClickListener {
            line_preview.gone()
            viewModel.getTestsGame(intent.getIntExtra("id", 1))
            back.gone()
        }
    }


    fun setEditWeb(){
        a.setOnTouchListener { v, event ->
            Log.e("sadsa","dssdsd")
            return@setOnTouchListener true
        }
        b.setOnTouchListener { v, event ->
            Log.e("sadsa","dssdsd")
            return@setOnTouchListener true
        }
        c.setOnTouchListener { v, event ->
            Log.e("sadsa","dssdsd")
            return@setOnTouchListener true
        }
        d.setOnTouchListener { v, event ->
            Log.e("sadsa","dssdsd")
            return@setOnTouchListener true
        }
        e.setOnTouchListener { v, event ->
            Log.e("sadsa","dssdsd")
            return@setOnTouchListener true
        }
        question.setOnTouchListener { v, event ->
            Log.e("sadsa","dssdsd")
            return@setOnTouchListener true
        }
    }




    override fun onBackPressed() {

        if (line_preview.visibility == View.VISIBLE){
            finish()
        }else{
            toast(getString(R.string.cant_finish))
        }

    }

    private fun getTime(quizzes: ArrayList<Quiz>) {

        var time = 0

        for (quiz in quizzes) {
            time += quiz.duration
        }

        setTime(time.toLong())

    }
    lateinit var cT:CountDownTimer
    private fun setTime(millis: Long) {
         cT = object : CountDownTimer(millis * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {


                val v = String.format("%02d", millisUntilFinished / 60000)
                val va = (millisUntilFinished % 60000 / 1000).toInt()
                time.text = v + ":" + String.format("%02d", va)
            }

            override fun onFinish() {
                line_quiz.gone()
                line_result.visible()
                setResultRV()
            }
        }
        cT.start()
    }

    override fun onClick(v: View?) {
        try {
            var quiz = quizzes[currentPosition]
            if (v != null) {
                when (v.id) {
                    R.id.choose_a -> {
                        if (quiz.choosenPosition == null) {


                            quiz.choosenPosition = 1
                            setTrueAnswer(choose_a, quiz.choosenPosition == quiz.true_answer)

                        }
                    }
                    R.id.line_b -> {
                        if (quiz.choosenPosition == null) {
                            quiz.choosenPosition = 2

                            setTrueAnswer(choose_b, quiz.choosenPosition == quiz.true_answer)

                        }
                    }
                    R.id.line_c -> {
                        if (quiz.choosenPosition == null) {

                            quiz.choosenPosition = 3
                            setTrueAnswer(choose_c, quiz.choosenPosition == quiz.true_answer)

                        }
                    }
                    R.id.line_d -> {
                        if (quiz.choosenPosition == null) {


                            quiz.choosenPosition = 4
                            setTrueAnswer(choose_d, quiz.choosenPosition == quiz.true_answer)

                        }
                    }
                    R.id.line_e -> {
                        if (quiz.choosenPosition == null) {
                            quiz.choosenPosition = 5
                            setTrueAnswer(choose_e, quiz.choosenPosition == quiz.true_answer)

                        }
                    }
                    R.id.next -> {
                        if (currentPosition < quizzes.size - 1) {
                            currentPosition++
                            setQuestion()
                            //adapter.setChoosePosition(currentPosition)
                        } else {
                            if (mInterstitialAd.isLoaded) {
                                mInterstitialAd.show()
                            } else {
                                Log.e("TAG", "The interstitial wasn't loaded yet.")
                            }
                            Handler().postDelayed({
                                line_quiz.gone()
                                line_result.visible()
                                setResultRV()
                                cT.cancel()
                            }, 2000)

                        }
                    }
                    R.id.ok -> {
                        viewModel.postGameCache(trues, getQuizIds(), 1,intent.getStringExtra("title"))
                        ok.isEnabled = false
                    }
                }
            }
        }catch (e:Exception){

        }
    }

    override fun onPause() {
        super.onPause()


    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            cT.cancel()
        }catch (e:Exception){

        }
    }

    override fun onResume() {
        super.onResume()

        App.activity=this

    }

    var trues = 0
    @SuppressLint("SetTextI18n")
    private fun setResultRV() {

        trues = 0

        for (quiz in quizzes) {

            if (quiz.choosenPosition == quiz.true_answer) {
                trues++
            }
        }

        point.text = "$trues - -"

        name_outer.text = Shared.name_outer
        Glide.with(this).load(kg.mvvmdordoi.ui.test.test_detail.Shared.user.avatar).into(image1)
        Glide.with(this).load(Shared.avatar_outer).into(image2)

    }

    fun getQuizIds(): String {

        var ids: String = ""

        for (quiz in quizzes) {
            ids += quiz.id.toString() + ","
        }
        return ids.substring(0, ids.length - 1)
    }


    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    private fun setQuestion() {


        if (currentPosition == quizzes.size - 1) {
            next.text = getString(R.string.comlete)
        } else {
            next.text = getString(R.string.dalee)
        }
        test_number.text = "Вопрос: ${currentPosition + 1}/${quizzes.size}"
        choose_a.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_b.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_c.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_d.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_e.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)

        line_e.visible()
        var quiz = quizzes[currentPosition]

        if (quiz.answer_e.isNullOrEmpty()) {
            line_e.gone()
        }
        Log.e("D_ANSWER",quiz.answer_d)
        line_d.visible()
        if (quiz.answer_d.isNullOrEmpty()){
            line_d.gone()
        }
        question.settings.javaScriptEnabled = true

        loadWeb(question,quiz.question)
        loadWeb(a,quiz.answer_a)
        loadWeb(b,quiz.answer_b)
        loadWeb(c,quiz.answer_c)
        loadWeb(d,quiz.answer_d)
        loadWeb(e,quiz.answer_e)

        //question.loadDataWithBaseURL("", "<html><body>"+quiz.question+"</body></html>", "text/html", "UTF-8", "")
    }

    fun loadWeb(webView: WebView,text:String){
        webView.loadDataWithBaseURL(
            null,
            "<html><body>" + correctImage(text) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8",
            null
        );
    }

    private fun correctImage(data: String): String {

        var true_data = data.replace(
            "/media/django-summernote",
            URL1 + "/media/django-summernote"
        )

        Log.e("TRUEDATA", true_data)

        return true_data
    }

    private fun setOnclickListeners() {
        next.setOnClickListener(this)
        choose_a.setOnClickListener(this)
        line_b.setOnClickListener(this)
        line_c.setOnClickListener(this)
        line_d.setOnClickListener(this)
        line_e.setOnClickListener(this)
        ok.setOnClickListener(this)

    }


    private fun setupRv() {
        val layoutManager = GridLayoutManager(this, 5)
        rv.layoutManager = layoutManager
        adapter = NumerationRvAdapter(this, this)
        rv.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            android.R.id.home -> finish()
        }

        return true
    }

    private fun setTrueAnswer(trueText: TextView, result: Boolean) {

        if (result) {
            sum += 10
            trueText.setBackgroundResource(R.drawable.blue_stroke_1dp_circle)
        } else {
            sum -= 7
            trueText.setBackgroundResource(R.drawable.red_stroke_1dp_circle)
        }
    }

    private fun setFalseAnswer(trueText: TextView, falseText: TextView) {
        trueText.setBackgroundResource(R.drawable.blue_stroke_1dp)
        falseText.setBackgroundResource(R.drawable.red_stroke_1dp)
        sum -= 7
    }

    private fun getTrueAnswer(position: Int): TextView {
        return when (position) {
            1 -> choose_a
            2 -> choose_b
            3 -> choose_c
            4 -> choose_d
            5 -> choose_e
            else -> choose_a
        }
    }
}
