package kg.mvvmdordoi.ui.test.test_detail

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import kg.mvvmdordoi.App
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Quiz
import android.view.View
import android.widget.TextView
import kg.mvvmdordoi.R
import kg.mvvmdordoi.utils.extension.gone
import kg.mvvmdordoi.utils.extension.visible
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_question.a
import kotlinx.android.synthetic.main.activity_question.b
import kotlinx.android.synthetic.main.activity_question.c
import kotlinx.android.synthetic.main.activity_question.d
import kotlinx.android.synthetic.main.activity_question.e
import kotlinx.android.synthetic.main.activity_question.next
import kotlinx.android.synthetic.main.activity_question.question
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.utils.URL1
import kg.mvvmdordoi.utils.extension.toast
import kotlinx.android.synthetic.main.activity_emp.*
import kotlinx.android.synthetic.main.activity_question.back
import kotlinx.android.synthetic.main.activity_question.choose_a
import kotlinx.android.synthetic.main.activity_question.choose_b
import kotlinx.android.synthetic.main.activity_question.choose_c
import kotlinx.android.synthetic.main.activity_question.choose_d
import kotlinx.android.synthetic.main.activity_question.choose_e
import kotlinx.android.synthetic.main.activity_question.line_a
import kotlinx.android.synthetic.main.activity_question.line_b
import kotlinx.android.synthetic.main.activity_question.line_c
import kotlinx.android.synthetic.main.activity_question.line_d
import kotlinx.android.synthetic.main.activity_question.line_e
import kotlinx.android.synthetic.main.activity_question.line_quiz
import kotlinx.android.synthetic.main.activity_question.line_result
import kotlinx.android.synthetic.main.activity_question.ok
import kotlinx.android.synthetic.main.activity_question.result
import kotlinx.android.synthetic.main.activity_question.rv
import kotlinx.android.synthetic.main.activity_question.test_number
import kotlinx.android.synthetic.main.activity_question.time
import kotlinx.android.synthetic.main.activity_question.toolbar
import kotlinx.android.synthetic.main.activity_question_owner.*
import java.util.*
import kotlin.collections.ArrayList


class QuestionActivity : AppCompatActivity(), NumerationListener, View.OnClickListener {

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

        setContentView(R.layout.activity_question)
        setSupportActionBar(toolbar)
        setAd()
        back.setOnClickListener {
            if (line_result.visibility == View.VISIBLE) {
                finish()
            } else {
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                    Handler().postDelayed(Runnable { finish() },6111)

                } else {
                    Log.e("TAG", "The interstitial wasn't loaded yet.")
                }
            }
        }
        var title = "Тест"
        title = intent.getStringExtra("title")
        supportActionBar!!.title = title.toString()

        App.activity = this
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(QuestionViewModel::class.java)

        setupRv()
        setEditWeb()

        viewModel.tests.observe(this, Observer {
            getTime(it as ArrayList<Quiz>)

            quizzes = it
            currentPosition = 0
            setQuestion()
        })

        viewModel.getTests(intent.getIntExtra("id", 1))

        setOnclickListeners()
        title = intent.getStringExtra("title_category")

        if (title.contains("Окуу жана") || title.contains("Чтение и")) {
            content.visible()
            content.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        ContentActivity::class.java
                    ).putExtra("desc", intent.getStringExtra("desc"))
                )
            }
        } else {
            content.gone()
        }
        if (Lang.get(this) == "1") {
            val locale = Locale("ky")
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)
        } else {
            val locale = Locale("ru")
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)
        }
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

    private fun getTime(quizzes: ArrayList<Quiz>) {

        var time = 0

        for (quiz in quizzes) {
            time += quiz.duration
        }

        setTime(time.toLong())

    }

    lateinit var cT: CountDownTimer

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
                result.text = sum.toString()
                setResultRV()
            }
        }
        cT.start()
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

    override fun onClick(v: View?) {
        var quiz = quizzes[currentPosition]
        if (v != null) {
            when (v.id) {
                R.id.line_a -> {
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
                        adapter.setChoosePosition(currentPosition)
                    } else {
                        if (mInterstitialAd.isLoaded) {
                            mInterstitialAd.show()
                        } else {
                            Log.e("TAG", "The interstitial wasn't loaded yet.")
                        }



                        Handler().postDelayed({
                            line_quiz.gone()
                            line_result.visible()
                            sum = 0

                            for (quizz in quizzes) {

                                if (quizz.choosenPosition == quizz.true_answer) {
                                    sum += 10
                                } else {
                                    sum -= 7
                                }

                            }
                            result.text = sum.toString()
                            setResultRV()
                            cT.cancel()
                            viewModel.getRating(sum, trues, quizzes.size - trues)

                        }, 2000)



                    }
                }
                R.id.ok -> {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
    }

    var trues = 0
    @SuppressLint("SetTextI18n")
    private fun setResultRV() {
        adapter.swapData(quizzes)

        trues = 0

        for (quiz in quizzes) {

            if (quiz.choosenPosition == quiz.true_answer) {
                trues++
            }
        }

        true_quiz.text = getString(R.string.truesd) + trues + "/" + quizzes.size

        var percent: Double = ((trues.toDouble() / quizzes.size.toDouble()).toDouble())

        percent *= 100

        if (percent > 80) {
            image_status.setImageResource(R.drawable.happy)
            text_status.text = getString(R.string.excelent)
        } else if (percent > 60) {
            image_status.setImageResource(R.drawable.wink)
            text_status.text = getString(R.string.good)
        } else if (percent > 40) {
            image_status.setImageResource(R.drawable.sad)
            text_status.text = getString(R.string.not_bad)
        } else {
            image_status.setImageResource(R.drawable.crying)
            text_status.text = getString(R.string.bad)
        }


    }

    override fun onBackPressed() {
        if (line_result.visibility == View.VISIBLE) {
            finish()
        } else {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
                Handler().postDelayed(Runnable { finish() },6111)
            } else {
                Log.e("TAG", "The interstitial wasn't loaded yet.")
            }
//            toast(getString(R.string.no_exit))
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    private fun setQuestion() {

        if (currentPosition == quizzes.size - 1) {
            next.text = getString(R.string.comlete)
        } else {
            next.text = getString(R.string.dalee)
        }

        test_number.text = getString(R.string.question)  + (currentPosition + 1) + "/" + quizzes.size
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
        if (quiz.answer_d.isNullOrEmpty()){
            line_d.gone()
        }
        question.settings.javaScriptEnabled = true

        question.loadData(
            "<html><body>" + correctImage(quiz.question) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8"
        );
        a.loadData(
            "<html><body>" + correctImage(quiz.answer_a) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8"
        );
        b.loadData(
            "<html><body>" + correctImage(quiz.answer_b) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8"
        );
        c.loadData(
            "<html><body>" + correctImage(quiz.answer_c) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8"
        );
        d.loadData(
            "<html><body>" + correctImage(quiz.answer_d) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8"
        );
        e.loadData(
            "<html><body>" + correctImage(quiz.answer_e) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8"
        );
        //question.loadDataWithBaseURL("", "<html><body>"+quiz.question+"</body></html>", "text/html", "UTF-8", "")


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
        line_a.setOnClickListener(this)
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
        trueText.setBackgroundResource(R.drawable.blue_stroke_1dp_circle)
        if (result) {
            sum += 10
        } else {
            sum -= 7
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
