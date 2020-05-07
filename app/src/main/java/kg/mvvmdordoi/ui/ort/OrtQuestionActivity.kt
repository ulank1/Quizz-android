package kg.mvvmdordoi.ui.ort

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import com.google.android.gms.ads.InterstitialAd
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.ui.ort.Ort.*
import kg.mvvmdordoi.ui.test.test_detail.ContentActivity
import kg.mvvmdordoi.ui.test.test_detail.NumerationListener
import kg.mvvmdordoi.ui.test.test_detail.NumerationRvAdapter
import kg.mvvmdordoi.utils.URL1
import kotlinx.android.synthetic.main.activity_question_ort.*
import java.util.*
import kotlin.collections.ArrayList


class OrtQuestionActivity : AppCompatActivity(), NumerationListener, View.OnClickListener {

    private var sum = 0
    override fun clickNumeration(position: Int) {
        currentPosition = position
        setQuestion()
    }

    var currentPosition = 0
    private lateinit var quizzes: ArrayList<Quiz>
    private lateinit var viewModel: OrtQuestionViewModel
    lateinit var adapter: NumerationOrtRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.activity = this

        setContentView(R.layout.activity_question_ort)
        setSupportActionBar(toolbar)
        ok.isEnabled = false
        back.setOnClickListener {
            if (line_result.visibility == View.VISIBLE) {
                finish()
            } else {
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                    Handler().postDelayed(Runnable { finish() }, 6111)

                } else {
                    Log.e("TAG", "The interstitial wasn't loaded yet.")
                }
            }
        }


        App.activity = this
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(OrtQuestionViewModel::class.java)

        setupRv()
        setEditWeb()

        viewModel.tests.observe(this, Observer {
            getTime()
            ok.isEnabled = true
            quizzes = it as ArrayList<Quiz>
            currentPosition = 0
            setQuestion()
            adapter.swapData(it)
        })

        viewModel.getTests(typeOfTest, intent.getIntExtra("category", 1))

        setOnclickListeners()

        if (typeOfTest == 3) {
            content.visible()
            content1.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        ContentActivity::class.java
                    ).putExtra("desc", text1)
                )
            }
            content2.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        ContentActivity::class.java
                    ).putExtra("desc", text2)
                )
            }
            content3.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        ContentActivity::class.java
                    ).putExtra("desc", text3)
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


    private fun getTime() {

        setTime(ortTimes[typeOfTest].toLong() * 60)

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
                setResultRV()
            }
        }
        cT.start()
    }

    fun setEditWeb() {
        a.setOnTouchListener { v, event ->
            Log.e("sadsa", "dssdsd")
            return@setOnTouchListener true
        }
        b.setOnTouchListener { v, event ->
            Log.e("sadsa", "dssdsd")
            return@setOnTouchListener true
        }
        c.setOnTouchListener { v, event ->
            Log.e("sadsa", "dssdsd")
            return@setOnTouchListener true
        }
        d.setOnTouchListener { v, event ->
            Log.e("sadsa", "dssdsd")
            return@setOnTouchListener true
        }
        e.setOnTouchListener { v, event ->
            Log.e("sadsa", "dssdsd")
            return@setOnTouchListener true
        }
        question.setOnTouchListener { v, event ->
            Log.e("sadsa", "dssdsd")
            return@setOnTouchListener true
        }
    }

    override fun onResume() {
        super.onResume()
        App.activity = this

    }

    override fun onClick(v: View?) {
        var quiz = quizzes[currentPosition]
        if (v != null) {
            when (v.id) {
                R.id.line_a -> {


                        quiz.choosenPosition = 1

                        setTrueAnswer(choose_a, quiz.choosenPosition == quiz.true_answer)


                }
                R.id.line_b -> {


                        quiz.choosenPosition = 2
                        setTrueAnswer(choose_b, quiz.choosenPosition == quiz.true_answer)


                }
                R.id.line_c -> {

                        quiz.choosenPosition = 3
                        setTrueAnswer(choose_c, quiz.choosenPosition == quiz.true_answer)

                }
                R.id.line_d -> {


                        quiz.choosenPosition = 4
                        setTrueAnswer(choose_d, quiz.choosenPosition == quiz.true_answer)

                }
                R.id.line_e -> {
                        quiz.choosenPosition = 5
                        setTrueAnswer(choose_e, quiz.choosenPosition == quiz.true_answer)

                }
                R.id.btn_finish -> {
                    cT.cancel()
                    setResultRV()
                }
                R.id.ok -> {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                R.id.next -> {
                    currentPosition++
                    setQuestion()
                    adapter.setChoosePosition(currentPosition)
                }
                R.id.previous -> {
                    currentPosition--
                    setQuestion()
                    adapter.setChoosePosition(currentPosition)
                }
            }
        }
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

        ortPoints[typeOfTest] = trues
        typeOfTest++

        if (typeOfTest==5){
            startActivity(Intent(this, ResultActivity::class.java))
        }else {
            startActivity(Intent(this, InfoOrtActivity::class.java))
        }
        finish()

    }

    override fun onBackPressed() {
//        if (line_result.visibility == View.VISIBLE) {
//            finish()
//        } else {
//            if (mInterstitialAd.isLoaded) {
//                mInterstitialAd.show()
//                Handler().postDelayed(Runnable { finish() }, 6111)
//            } else {
//                Log.e("TAG", "The interstitial wasn't loaded yet.")
//            }
////            toast(getString(R.string.no_exit))
//        }
    }

    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    private fun setQuestion() {

        if (quizzes.size-1==currentPosition){
            btn_finish.visible()
            next.gone()
        }else{
            next.gone()
            btn_finish.gone()
        }

        if (currentPosition==0){
            previous.gone()
        }else{
            previous.visible()
        }
        test_number.text = getString(R.string.question) + (currentPosition + 1) + "/" + quizzes.size
        choose_a.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_b.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_c.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_d.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_e.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)

        line_e.visible()
        var quiz = quizzes[currentPosition]

        if (quiz.choosenPosition != null) {
            when {
                quiz.choosenPosition == 0 -> setChooseAnswer(choose_a)
                quiz.choosenPosition == 1 -> setChooseAnswer(choose_b)
                quiz.choosenPosition == 2 -> setChooseAnswer(choose_c)
                quiz.choosenPosition == 3 -> setChooseAnswer(choose_d)
                quiz.choosenPosition == 4 -> setChooseAnswer(choose_e)
            }
        }

        if (quiz.answer_e.isNullOrEmpty()) {
            line_e.gone()
        }
        if (quiz.answer_d.isNullOrEmpty()) {
            line_d.gone()
        }
        question.settings.javaScriptEnabled = true
        a.settings.javaScriptEnabled = true
        b.settings.javaScriptEnabled = true
        c.settings.javaScriptEnabled = true
        d.settings.javaScriptEnabled = true
        e.settings.javaScriptEnabled = true

        question.loadDataWithBaseURL(
            null,
            "<html><body>" + correctImage(quiz.question) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8",
            null
        );
        a.loadDataWithBaseURL(
            null,
            "<html><body>" + correctImage(quiz.answer_a) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8",
            null
        );
        b.loadDataWithBaseURL(
            null,
            "<html><body>" + correctImage(quiz.answer_b) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8",
            null
        );
        c.loadDataWithBaseURL(
            null,
            "<html><body>" + correctImage(quiz.answer_c) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8",
            null
        );
        d.loadDataWithBaseURL(
            null,
            "<html><body>" + correctImage(quiz.answer_d) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8",
            null
        );
        e.loadDataWithBaseURL(
            null,
            "<html><body>" + correctImage(quiz.answer_e) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8",
            null
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
        btn_finish.setOnClickListener(this)
        line_a.setOnClickListener(this)
        line_b.setOnClickListener(this)
        line_c.setOnClickListener(this)
        line_d.setOnClickListener(this)
        line_e.setOnClickListener(this)
        ok.setOnClickListener(this)
        test_number.setOnClickListener {
            line_number.visible()
        }
        btn_close.setOnClickListener {
            line_number.gone()
        }

    }


    private fun setupRv() {
        val layoutManager = GridLayoutManager(this, 5)
        rv.layoutManager = layoutManager
        adapter = NumerationOrtRvAdapter(this, this)
        rv.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
//            android.R.id.home -> finish()
        }

        return true
    }

    private fun setTrueAnswer(trueText: TextView, result: Boolean) {
        choose_a.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_b.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_c.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_d.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        choose_e.setBackgroundResource(R.drawable.gray_stroke_1dp_circle)
        trueText.setBackgroundResource(R.drawable.question_stroke_1dp_circle)
        if (result) {
            sum += 10
        } else {
            sum -= 7
        }
    }


    private fun setChooseAnswer(trueText: TextView) {
        trueText.setBackgroundResource(R.drawable.question_stroke_1dp_circle)
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
