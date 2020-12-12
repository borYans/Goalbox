package com.boko.goalboxsoccerprediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.goalboxsoccerprediction.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayHomeProgress: TextView = findViewById(R.id.homeTeamGoalsTxt)
        val displayAwayProgress: TextView = findViewById(R.id.awayTeamGoalsTxt)
        val homeTeamGoals: SeekBar = findViewById(R.id.homeSeekBar)
        val awayTeamGoals: SeekBar = findViewById(R.id.awaySeekBar)
        val calcExpectedResultBtn: Button = findViewById(R.id.calculateResultBtn)

        homeTeamGoals.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                displayHomeProgress.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        awayTeamGoals.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                displayAwayProgress.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        calcExpectedResultBtn.setOnClickListener {
                val intent = Intent(this, PredictionResultActivity::class.java)
                intent.putExtra("RESULT", calcTheAverageResult(homeTeamGoals, awayTeamGoals))
                startActivity(intent)
                return@setOnClickListener

        }
    }

    private fun calcTheAverageResult(homeProgress: SeekBar, awayProgress: SeekBar) = ((homeProgress.progress + awayProgress.progress) / 20.0)

    override fun onBackPressed() {
        val exitIntent = Intent(Intent.ACTION_MAIN)
        exitIntent.addCategory(Intent.CATEGORY_HOME)
        exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(exitIntent)
    }


}