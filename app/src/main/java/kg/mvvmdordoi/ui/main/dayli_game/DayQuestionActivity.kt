package kg.mvvmdordoi.ui.main.dayli_game

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import kg.mvvmdordoi.App
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Quiz
import android.view.View
import android.widget.TextView
import kg.mvvmdordoi.R
import kg.mvvmdordoi.utils.extension.gone
import kg.mvvmdordoi.utils.extension.visible
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import kg.mvvmdordoi.network.Day
import kg.mvvmdordoi.ui.test.test_detail.NumerationListener
import kg.mvvmdordoi.ui.test.test_detail.NumerationRvAdapter
import kg.mvvmdordoi.utils.URL1
import kg.mvvmdordoi.utils.extension.getTodayDateDot
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_question_day.*
import kotlinx.android.synthetic.main.activity_question_day.a
import kotlinx.android.synthetic.main.activity_question_day.b
import kotlinx.android.synthetic.main.activity_question_day.c
import kotlinx.android.synthetic.main.activity_question_day.choose_a
import kotlinx.android.synthetic.main.activity_question_day.choose_b
import kotlinx.android.synthetic.main.activity_question_day.choose_c
import kotlinx.android.synthetic.main.activity_question_day.choose_d
import kotlinx.android.synthetic.main.activity_question_day.choose_e
import kotlinx.android.synthetic.main.activity_question_day.d
import kotlinx.android.synthetic.main.activity_question_day.e
import kotlinx.android.synthetic.main.activity_question_day.line_a
import kotlinx.android.synthetic.main.activity_question_day.line_b
import kotlinx.android.synthetic.main.activity_question_day.line_c
import kotlinx.android.synthetic.main.activity_question_day.line_d
import kotlinx.android.synthetic.main.activity_question_day.line_e
import kotlinx.android.synthetic.main.activity_question_day.line_quiz
import kotlinx.android.synthetic.main.activity_question_day.line_result
import kotlinx.android.synthetic.main.activity_question_day.next
import kotlinx.android.synthetic.main.activity_question_day.ok
import kotlinx.android.synthetic.main.activity_question_day.question
import kotlinx.android.synthetic.main.activity_question_day.result
import kotlinx.android.synthetic.main.activity_question_day.rv
import kotlinx.android.synthetic.main.activity_question_day.test_number
import kotlinx.android.synthetic.main.activity_question_day.time
import java.lang.Exception


class DayQuestionActivity : Fragment(), NumerationListener, View.OnClickListener {

    private var sum = 0
    override fun clickNumeration(position: Int) {
        currentPosition = position
        setQuestion()
    }

    var currentPosition = 0
    private lateinit var quizzes: ArrayList<Quiz>
    private lateinit var viewModel: QuestionDayViewModel
    lateinit var adapter: NumerationRvAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_question_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(QuestionDayViewModel::class.java)

        setupRv()

        line_no_one.setOnClickListener {  }

        viewModel.getQuote()
        viewModel.quote.observe(this, Observer { quote.text = it!!.quote })


        viewModel.tests.observe(this, Observer {

            if (!it.isNullOrEmpty()) {
                getTime(it as ArrayList<Quiz>)

                quizzes = it
                currentPosition = 0
                setQuestion()
            }else{
                line_no_one.visible()
            }
        })
        if (Day.get(context!!) != getTodayDateDot()) {
            Log.e("Today", getTodayDateDot())
            viewModel.getTestsDay()
        }else{
            line_no_one.visible()
        }

       // viewModel.getTestsDay()
        setOnclickListeners()
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
                result.text = sum.toString()
            }
        }
        cT.start()
    }

    override fun onClick(v: View?) {
        Log.e("Onclick","Onclick")
        var quiz = quizzes[currentPosition]
        if (v != null) {
            when (v.id) {
                R.id.line_a -> {
                    if (quiz.choosenPosition == null) {

                        Log.e("kdkdkd","ausaasaso")
                        quiz.choosenPosition = 1
                        setTrueAnswer(choose_a,quiz.choosenPosition==quiz.true_answer)

                    }
                }
                R.id.line_b -> {
                    if (quiz.choosenPosition == null) {

                        quiz.choosenPosition = 2
                        setTrueAnswer(choose_b,quiz.choosenPosition==quiz.true_answer)

                    }
                }
                R.id.line_c -> {
                    if (quiz.choosenPosition == null) {

                        quiz.choosenPosition = 3
                        setTrueAnswer(choose_c,quiz.choosenPosition==quiz.true_answer)

                    }
                }
                R.id.line_d -> {
                    if (quiz.choosenPosition == null) {


                        quiz.choosenPosition = 4
                        setTrueAnswer(choose_d,quiz.choosenPosition==quiz.true_answer)

                    }
                }
                R.id.line_e -> {
                    if (quiz.choosenPosition == null) {
                        quiz.choosenPosition = 5
                        setTrueAnswer(choose_e,quiz.choosenPosition==quiz.true_answer)

                    }
                }
                R.id.next -> {
                    if (currentPosition < quizzes.size - 1) {
                        currentPosition++
                        setQuestion()
                        adapter.setChoosePosition(currentPosition)
                    } else {
                        line_quiz.gone()
                        line_result.visible()
                        result.text = sum.toString()
                        //setResultRV()
                        Day.save(getTodayDateDot(),context!!)
                        viewModel.getRating(sum)

                    }
                }
                R.id.ok -> {
                    line_no_one.visible()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            cT.cancel()
        }catch (e:Exception){

        }
    }

    private fun setResultRV() {
        adapter.swapData(quizzes)
    }





    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    private fun setQuestion() {



        if (currentPosition == quizzes.size - 1) {
            next.text = getString(R.string.comlete)
        } else {
            next.text = getString(R.string.dalee)
        }
        test_number.text = "Вопрос: ${currentPosition+1}/${quizzes.size}"
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
            "$URL1/media/django-summernote"
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
        val layoutManager = GridLayoutManager(context,5)
        rv.layoutManager = layoutManager
        adapter = NumerationRvAdapter(App.activity!!, this)
        rv.adapter = adapter
    }



    private fun setTrueAnswer(trueText: TextView,result:Boolean) {
        trueText.setBackgroundResource(R.drawable.blue_stroke_1dp_circle)
        if (result) {
            sum += 30
        }else{
            sum -= 21
        }
    }

    private fun setFalseAnswer(trueText: TextView, falseText: TextView) {
        trueText.setBackgroundResource(R.drawable.blue_stroke_1dp)
        falseText.setBackgroundResource(R.drawable.red_stroke_1dp)
        sum -= 21
    }

    override fun onPause() {
        super.onPause()
        try {
            cT.cancel()
        }catch (e:Exception){

        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("ONSTART","llll")
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
