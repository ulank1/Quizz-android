package kg.mvvmdordoi.ui.test.test_detail

import java.util.Random

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import kg.mvvmdordoi.R
import kotlinx.android.synthetic.main.fragment_quiz.*

class PageFragment : Fragment() {

    internal var pageNumber: Int = 0
    internal var backColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments!!.getInt(ARGUMENT_PAGE_NUMBER)

        val rnd = Random()
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, null)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var quiz = Shared.quizzes[pageNumber]

        question.text = quiz.question
        a.text = quiz.answer_a
        b.text = quiz.answer_b
        c.text = quiz.answer_c
        d.text = quiz.answer_d
        e.text = quiz.answer_e


    }




    companion object {

        internal val ARGUMENT_PAGE_NUMBER = "arg_page_number"

        internal fun newInstance(page: Int): PageFragment {
            val pageFragment = PageFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }
}