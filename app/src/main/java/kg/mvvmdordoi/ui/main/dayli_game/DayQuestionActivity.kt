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
import android.widget.EditText
import kg.mvvmdordoi.model.get.Comment
import kg.mvvmdordoi.model.get.DayQuiz
import kg.mvvmdordoi.utils.URL1
import kotlinx.android.synthetic.main.activity_question_day.*
import kotlinx.android.synthetic.main.activity_question_day.question
import kotlinx.android.synthetic.main.activity_question_day.rv
import java.lang.Exception


class DayQuestionActivity : Fragment(),CommentClickListener{
    override fun likeClick(comment: Int) {

    }

    override fun unlikeClick(comment: Int) {

    }

    override fun commentClick(name: String?, comment: Int) {
        idComment = comment
        user.text = "$name,"
        user.visible()
    }

    private var idComment = 0

    private lateinit var quizzes: ArrayList<DayQuiz>
    private lateinit var viewModel: QuestionDayViewModel
    private lateinit var message: EditText
    lateinit var adapter: CommentRvAdapter



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

        line_no_one.setOnClickListener { }

        message = view.findViewById(R.id.message)
        viewModel.comments.observe(this, Observer { adapter.swapData(it as ArrayList<Comment>) })
        viewModel.tests.observe(this, Observer {

            if (!it.isNullOrEmpty()) {
                quizzes = it as ArrayList<DayQuiz>
                setQuestion()
            } else {
                line_no_one.visible()
            }

        })

        user.setOnClickListener { user.gone() }
        btn_send.setOnClickListener {

            if (user.visibility==View.GONE){
                if (message.text.toString().isNotEmpty()) {
                    viewModel.sendComment(message.text.toString(),quizzes[0].id)
                }
            }else{
                if (message.text.toString().isNotEmpty()) {
                    viewModel.sendAnswer(message.text.toString(),quizzes[0].id,idComment)
                }
            }
            user.gone()
            message.setText("")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTestsDay()
    }

    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    private fun setQuestion() {
        var quiz = quizzes[0]

        question.settings.javaScriptEnabled = true

        question.loadData(
            "<html><body>" + correctImage(quiz.question) + "</body></html>",
            "text/html; charset=utf-8",
            "UTF-8"
        )

        viewModel.getComments(quiz.id)
    }

    private fun correctImage(data: String): String {

        var true_data = data.replace(
            "/media/django-summernote",
            "$URL1/media/django-summernote"
        )

        Log.e("TRUEDATA", true_data)

        return true_data
    }

    private fun setupRv() {
        val layoutManager = GridLayoutManager(context, 1)
        rv.layoutManager = layoutManager
        adapter = CommentRvAdapter(App.activity!!,this)
        rv.adapter = adapter
    }


    override fun onPause() {
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
        Log.e("ONSTART", "llll")
    }

}
