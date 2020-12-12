package com.boko.goalboxsoccerprediction

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.goalboxsoccerprediction.R
import kotlin.math.roundToInt

class PredictionResultActivity : AppCompatActivity() {


    companion object {
        private const val FIRST_CONDITION = 2.1
        private const val SECOND_CONDITION = 2.501
        private const val THIRD_CONDITION = 3.502
        private const val FOURTH_CONDITION = 7.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction_result)

        val reCalcExpectedResultBtn: Button = findViewById(R.id.reCalculateResultBtn)
        val goalNumber: TextView = findViewById(R.id.numberOfGoalsTxt)
        val percentageNumber: TextView = findViewById(R.id.percentageTxt)
        percentageNumber.setTextColor(ContextCompat.getColor(this, R.color.custom_thumb_seekbar_color))


        val avgResult = intent.getDoubleExtra("RESULT", 0.0)
        displayResult(avgResult, goalNumber, percentageNumber)

        reCalcExpectedResultBtn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    fun displayResult(averageResult: Double, goalNumber: TextView = findViewById(R.id.numberOfGoalsTxt), percentageNumber: TextView = findViewById(R.id.percentageTxt)) {

        when {
            averageResult < FIRST_CONDITION -> {
                if (overTwoGoals1(averageResult) > 70) percentageNumber.setTextColor(ContextCompat.getColor(this, R.color.calculateButtonColor))

                if (overTwoGoals1(averageResult) == 0) {
                    percentageNumber.text = "0%"
                } else {
                    dynamicDisplayGoals(2.0, goalNumber)
                    dynamicDisplayPercentage(percentageNumber, overTwoGoals1(averageResult))
                }
            }

            averageResult in FIRST_CONDITION..SECOND_CONDITION -> {
                if (overTwoGoals2(averageResult) > 70) percentageNumber.setTextColor(ContextCompat.getColor(this, R.color.calculateButtonColor))
                dynamicDisplayGoals(2.0, goalNumber)
                dynamicDisplayPercentage(percentageNumber, overTwoGoals2(averageResult))
            }

            averageResult in SECOND_CONDITION..THIRD_CONDITION -> {
                if (overThreeGoals(averageResult) > 70) percentageNumber.setTextColor(ContextCompat.getColor(this, R.color.calculateButtonColor))
                dynamicDisplayGoals(3.0, goalNumber)
                dynamicDisplayPercentage(percentageNumber, overThreeGoals(averageResult))
            }

            averageResult in THIRD_CONDITION..FOURTH_CONDITION -> {
                if (overFourGoals(averageResult) > 70) percentageNumber.setTextColor(ContextCompat.getColor(this, R.color.calculateButtonColor))
                dynamicDisplayGoals(4.0, goalNumber)
                dynamicDisplayPercentage(percentageNumber, overFourGoals(averageResult))
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun dynamicDisplayPercentage(percentageNumber: TextView = findViewById(R.id.percentageTxt), percentage: Int) {

        var counter = 0
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                counter++
                when (counter) {
                    percentage -> return
                    else -> percentageNumber.text = "$counter%"
                }
                mainHandler.postDelayed(this, 20)
            }
        })
    }

    private fun dynamicDisplayGoals(goals: Double, goalNumber: TextView = findViewById(R.id.numberOfGoalsTxt)) {

        var counter = 0.0
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                counter += 0.5
                when (counter) {
                    goals -> return
                    else -> goalNumber.text = "$counter"
                }
                mainHandler.postDelayed(this, 400)
            }
        })
    }

    private fun overTwoGoals1(averageGoalsResult: Double) = ((averageGoalsResult * 100 / FIRST_CONDITION)).roundToInt()
    private fun overTwoGoals2(averageGoalsResult: Double) = (averageGoalsResult * 100 / SECOND_CONDITION).roundToInt()
    private fun overThreeGoals(averageGoalsResult: Double) = (averageGoalsResult * 100 / THIRD_CONDITION).roundToInt()
    private fun overFourGoals(averageGoalsResult: Double) = (averageGoalsResult * 100 / FOURTH_CONDITION).roundToInt()

}
