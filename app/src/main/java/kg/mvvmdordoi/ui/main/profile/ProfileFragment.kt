package kg.mvvmdordoi.ui.main.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide


import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import kg.mvvmdordoi.App

import java.util.ArrayList

import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.GameOuter
import kg.mvvmdordoi.model.get.Rating
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.ui.auth.login.LoginActivity
import kg.mvvmdordoi.ui.auth.register.RegisterActivity
import kg.mvvmdordoi.ui.auth.register.RegisterViewModel
import kg.mvvmdordoi.ui.test.test_detail.Shared
import kg.mvvmdordoi.utils.extension.getTodayDate
import kg.mvvmdordoi.utils.extension.getTodayDateDot
import kg.mvvmdordoi.utils.extension.gone
import kotlinx.android.synthetic.main.profile_layout.*
import lecho.lib.hellocharts.gesture.ContainerScrollType
import lecho.lib.hellocharts.gesture.ZoomType
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.Line
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.view.LineChartView
import kotlin.math.max
import kotlin.math.min

class ProfileFragment : Fragment() {

    lateinit var logInBtn: Button
    lateinit var chart: LineChart
    private lateinit var viewModel: ProfileViewModel
    var max_sixe = 7
    var ratings:ArrayList<Rating> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.profile_layout, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(ProfileViewModel::class.java)
        viewModel.ratings.observe(this, Observer {
            ratings = it as ArrayList<Rating>

            setupChart(it as ArrayList<Rating>)
            setupTotals(it as ArrayList<Rating>)

        })

        viewModel.game.observe(this, Observer { setupGameInfo(it as ArrayList<GameOuter>) })

        viewModel.getGameOuter()
        viewModel.getRating()
        viewModel.getUser()
        viewModel.user.observe(this, Observer {

            nick_name.text = it?.name
            Glide.with(App.activity!!).load(it?.avatar).into(avatar)
            Shared.user = it
        })
        logInBtn = activity!!.findViewById(R.id.signIn)
        chart = view.findViewById(R.id.chart)

        logInBtn.setOnClickListener { startActivity(Intent(context, LoginActivity::class.java)) }

        val register = view.findViewById<Button>(R.id.register)
        register.setOnClickListener { startActivity(Intent(context, RegisterActivity::class.java)) }

        year.setOnClickListener {
            max_sixe = 365
            setupChart(ratings)
        }
        month.setOnClickListener {
            max_sixe = 30
            setupChart(ratings)
        }
        week.setOnClickListener {
            max_sixe = 7
            setupChart(ratings)
        }
    }


    private fun setupGameInfo(gameOuters:ArrayList<GameOuter>){

        var win = 0
        var lose = 0
        var check = 0


        for (game in gameOuters){
            var is_owner = UserToken.getToken(context!!)!!.toInt() == game.user_owner.id
            if (game.outer_point>-1) {
                if (game.owner_point > game.outer_point) {
                    if (is_owner) {
                        win++
                    } else {
                        lose++
                    }
                } else if (game.owner_point < game.outer_point) {
                    if (!is_owner) {
                        win++
                    } else {
                        lose++
                    }
                } else {
                    check++
                }
            }
        }

        duel_win.text = win.toString()
        duel_check.text = check.toString()
        duel_lose.text = lose.toString()

    }

    var todayPoint = 0
    var todayTrue = 0
    var todayFalse = 0

    @SuppressLint("SetTextI18n")
    private fun setupTotals(ratings: ArrayList<Rating>) {

        total_point.text = getSummPoints(ratings).toString()
        var total_quiz =todayFalse+todayTrue
        today_point.text = todayPoint.toString()
        today_quiz.text = (total_quiz).toString()
        if (total_quiz!=0) {
            true_quiz.text =
                (todayTrue).toString() + " (" +  String.format("%.1f",(todayTrue.toDouble() / total_quiz.toDouble() * 100)) + "%)"
            false_quiz.text =
               (todayFalse).toString() + " (" + String.format("%.1f",(todayFalse.toDouble() / total_quiz.toDouble() * 100)) + "%)"
        }else{
            true_quiz.text =
                (todayTrue).toString()
            false_quiz.text =
               (todayFalse).toString()
        }
    }
    private fun getSummPoints(ratings: ArrayList<Rating>):Int{

        var sum = 0
        for (rating in ratings) {
            if (rating.created_at != "all") {
                sum += rating.rating
                if (rating.created_at == getTodayDateDot()) {
                    todayPoint = rating.rating
                    todayTrue = rating.true_answer
                    todayFalse = rating.false_answer
                }
            }
        }

        return sum

    }

    override fun onResume() {
        super.onResume()
        Log.e("sadasd", "ONRESUME")
        if (UserToken.hasToken(context!!)){
            dont_auth.gone()
            viewModel.getGameOuter()
            viewModel.getRating()
            viewModel.getUser()
        }
    }

    private fun setupChart(ratings:ArrayList<Rating>) {
        if (ratings.size>1) {
            date1.text = ratings[0].created_at
            date2.text = ratings[ratings.size - 1].created_at
        }

        if (ratings.size>0) {
            Log.e("sds", "sdsdsd")

            val entries = ArrayList<Entry>()

            var lastSum:Float = 0F
            for (i in 0 until min(ratings.size, max_sixe)) {
                lastSum+=ratings[i].rating.toFloat()
                entries.add(Entry(i.toFloat(), lastSum))
            }

            val dataSet = LineDataSet(entries, "")
            dataSet.setDrawFilled(true)
            dataSet.setFillAlpha(65)
            dataSet.color = ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
            dataSet.fillColor = ContextCompat.getColor(context!!, R.color.colorPrimaryDarkTransparent)
            dataSet.valueTextColor = ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
            dataSet.lineWidth = 2F

            //****
            // Controlling X axis
//            val xAxis = chart.xAxis
//            // Set the xAxis position to bottom. Default is top
//            xAxis.position = XAxis.XAxisPosition.BOTTOM
//            //Customizing x axis value
//
//            val formatter = IAxisValueFormatter { value, axis -> ratings[value.toInt()].created_at }
//            xAxis.granularity = 1f // minimum axis-step (interval) is 1
//            xAxis.valueFormatter = formatter
////        xAxis.axisMaximum = max_sixe.toFloat()
////        xAxis.labelCount = min(max_sixe,ratings.size)
//
//            //***
//            // Controlling right side of y axis
//            val yAxisRight = chart.axisRight
//            yAxisRight.isEnabled = false
//
//            //***
//            // Controlling left side of y axis
//            val yAxisLeft = chart.axisLeft
//            yAxisLeft.granularity = 1f

            // Setting Data
            val data = LineData(dataSet)
            chart.data = data
            chart.animateX(10)

            chart.xAxis.setDrawAxisLine(false)
            chart.xAxis.isEnabled =false
            chart.description.isEnabled = false
            chart.legend.isEnabled = false
            //refresh
            chart.invalidate()
        }
    }

}
