package com.lemol.cardflash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var savedVocabs : SharedPreferences
    //Initiate SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedVocabs = getSharedPreferences("DictList", Context.MODE_PRIVATE)
        //This line saves the savedVocabs as DictList.xml?
        val editor = savedVocabs.edit()
        editor.putString("","")
        editor.apply()

        findViewById<Button>(R.id.initTest).setOnClickListener {
            //Turn on the testBox
            val toggleTest = findViewById<ConstraintLayout>(R.id.testBox).isVisible
            if (!toggleTest) {
                findViewById<ConstraintLayout>(R.id.testBox).isVisible = true
                resetTest()
            }
        }

        findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
            findViewById<ImageButton>(R.id.imageButton).isVisible = false
            findViewById<Button>(R.id.nextButton).isVisible = true
            findViewById<Button>(R.id.endButton).isVisible = true
        }

        findViewById<Button>(R.id.endButton).setOnClickListener {
            findViewById<ConstraintLayout>(R.id.testBox).isVisible = false
        }

        findViewById<Button>(R.id.nextButton).setOnClickListener {
            resetTest()
            newQuestion()
            Log.d("dblogger", "next question!")
        }

        findViewById<FloatingActionButton>(R.id.moveToList).setOnClickListener {
            startActivity(Intent(this,WordList::class.java))
        }
    }

    private fun resetTest () {
        findViewById<ImageButton>(R.id.imageButton).isVisible = true
        findViewById<Button>(R.id.nextButton).isVisible = false
        findViewById<Button>(R.id.endButton).isVisible = false
    }

    private fun newQuestion() {
        findViewById<TextView>(R.id.testQuestion).text = "new question!"
        findViewById<TextView>(R.id.testAnswer).text = "new answer!"
    }
}