package kg.mvvmdordoi.ui.test.test_detail

import android.annotation.SuppressLint
import android.app.Activity
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
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kg.mvvmdordoi.ui.game.users.Shared
import kg.mvvmdordoi.utils.URL1
import kg.mvvmdordoi.utils.extension.toast
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_question_owner.*
import kotlinx.android.synthetic.main.activity_question_owner_invite.*
import kotlinx.android.synthetic.main.activity_question_owner_invite.a
import kotlinx.android.synthetic.main.activity_question_owner_invite.b
import kotlinx.android.synthetic.main.activity_question_owner_invite.back
import kotlinx.android.synthetic.main.activity_question_owner_invite.btn_start
import kotlinx.android.synthetic.main.activity_question_owner_invite.c
import kotlinx.android.synthetic.main.activity_question_owner_invite.choose_a
import kotlinx.android.synthetic.main.activity_question_owner_invite.choose_b
import kotlinx.android.synthetic.main.activity_question_owner_invite.choose_c
import kotlinx.android.synthetic.main.activity_question_owner_invite.choose_d
import kotlinx.android.synthetic.main.activity_question_owner_invite.choose_e
import kotlinx.android.synthetic.main.activity_question_owner_invite.d
import kotlinx.android.synthetic.main.activity_question_owner_invite.e
import kotlinx.android.synthetic.main.activity_question_owner_invite.image1
import kotlinx.android.synthetic.main.activity_question_owner_invite.image11
import kotlinx.android.synthetic.main.activity_question_owner_invite.image2
import kotlinx.android.synthetic.main.activity_question_owner_invite.image21
import kotlinx.android.synthetic.main.activity_question_owner_invite.line_b
import kotlinx.android.synthetic.main.activity_question_owner_invite.line_c
import kotlinx.android.synthetic.main.activity_question_owner_invite.line_d
import kotlinx.android.synthetic.main.activity_question_owner_invite.line_e
import kotlinx.android.synthetic.main.activity_question_owner_invite.line_preview
import kotlinx.android.synthetic.main.activity_question_owner_invite.line_quiz
import kotlinx.android.synthetic.main.activity_question_owner_invite.line_result
import kotlinx.android.synthetic.main.activity_question_owner_invite.name
import kotlinx.android.synthetic.main.activity_question_owner_invite.name1
import kotlinx.android.synthetic.main.activity_question_owner_invite.name_outer
import kotlinx.android.synthetic.main.activity_question_owner_invite.name_outer1
import kotlinx.android.synthetic.main.activity_question_owner_invite.next
import kotlinx.android.synthetic.main.activity_question_owner_invite.ok
import kotlinx.android.synthetic.main.activity_question_owner_invite.point
import kotlinx.android.synthetic.main.activity_question_owner_invite.question
import kotlinx.android.synthetic.main.activity_question_owner_invite.rv
import kotlinx.android.synthetic.main.activity_question_owner_invite.test_number
import kotlinx.android.synthetic.main.activity_question_owner_invite.time
import kotlinx.android.synthetic.main.activity_question_owner_invite.toolbar
import java.lang.Exception


class GameQuestionActivityInvite : AppCompatActivity(), NumerationListener, View.OnClickListener {

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
        setContentView(R.layout.activity_question_owner_invite)
        setAd()
        setSupportActionBar(toolbar)

        var title = "Тест"
        title = intent.getStringExtra("title")
        supportActionBar!!.title = title.toString()
        back.setOnClickListener { finish() }
        category.text = intent.getStringExtra("title")
        App.activity = this
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(QuestionViewModel::class.java)
        setSSS()
        setupRv()

        viewModel.tests.observe(this, Observer {
            getTime(it as ArrayList<Quiz>)

            quizzes = it
            currentPosition = 0
            setQuestion()
        })

        setEditWeb()
        setOnclickListeners()
    }

    private lateinit var mInterstitialAd: InterstitialAd

    fun setAd() {
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

    private fun setSSS() {
        name_outer1.text = Shared.gameOuter.user_outer.name
        name1.text = Shared.gameOuter.user_owner.name

        Log.e("ImageII", Shared.gameOuter.user_owner.avatar)

        Glide.with(this).load(URL1 + Shared.gameOuter.user_owner.avatar).into(image11)
        Glide.with(this).load(URL1 + Shared.gameOuter.user_outer.avatar).into(image21)

        btn_start.setOnClickListener {
            line_preview.gone()
            viewModel.getTestsGameInvite(Shared.gameOuter.quiz)
            back.gone()
        }
    }

    override fun onBackPressed() {

        if (line_preview.visibility == View.VISIBLE) {
            finish()
        } else {
            toast(getString(R.string.cant_finish))
        }

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
                                setResultRV()
                                cT.cancel()
                                var ownerPoint = Shared.gameOuter.owner_point

                                when {
                                    ownerPoint > trues -> viewModel.getRating(-10, 0, 0)
                                    ownerPoint < trues -> viewModel.getRating(20, 0, 0)
                                    else -> viewModel.getRating(5, 0, 0)
                                }
                                viewModel.putGameCache(trues, Shared.gameOuter.id)
                            }, 2000)
                        }
                    }
                    R.id.ok -> {
                        setResult(Activity.RESULT_OK)
                        finish()

                    }
                }
            }
        } catch (e: Exception) {

        }
    }

    override fun onPause() {
        super.onPause()


    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            cT.cancel()
        } catch (e: Exception) {

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


        var text1 = if (Shared.gameOuter.outer_point == -1) {
            "${Shared.gameOuter.owner_point} - $trues"
        } else {
            "$trues - ${Shared.gameOuter.outer_point}"
        }

        // point.text = "$trues - -"

        point.text = text1

        name_outer.text = Shared.gameOuter.user_outer.name
        name.text = Shared.gameOuter.user_owner.name

        Glide.with(this).load(URL1 + Shared.gameOuter.user_owner.avatar).into(image1)
        Glide.with(this).load(URL1 + Shared.gameOuter.user_outer.avatar).into(image2)
        when {
            trues > Shared.gameOuter.owner_point -> {

                right_win.visible()
                text.visible()
                text.text = getString(R.string.you_win)
                point_result.text = getString(R.string.you_received) + " 20"
            }
            trues < Shared.gameOuter.owner_point -> {
                left_win.visible()
                text.visible()
                point_result.text = getString(R.string.you_received) + " -10"
                text.text = getString(R.string.you_lose)
            }
            else -> {
                text.visible()
                text.text = getString(R.string.draw)
                point_result.text = getString(R.string.you_received) + " 5"
            }
        }


    }

    fun getQuizIds(): String {

        var ids: String = ""

        for (quiz in quizzes) {
            ids += quiz.id.toString() + ","
        }
        return ids.substring(0, ids.length - 2)
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


    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    private fun setQuestion() {


        if (currentPosition == quizzes.size - 1) {
            next.text = getString(R.string.comlete)
        } else {
            next.text = getString(R.string.dalee)
        }
        test_number.text = getString(R.string.question) + (currentPosition + 1) + "/" + quizzes.size
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
        if (quiz.answer_d.isNullOrEmpty()) {
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
