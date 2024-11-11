package com.bardaval.eventtask

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Button>
    private lateinit var timerTextView: TextView
    private lateinit var validateButton: Button
    private lateinit var winnerNameEditText: EditText
    private lateinit var winnerDatabase: WinnerDatabase
    private lateinit var showWinnersButton: Button

    private val correctMagicSquare = arrayOf(8, 1, 6, 3, 5, 7, 4, 9, 2)
    private var startTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        winnerDatabase = WinnerDatabase.getDatabase(this)

        timerTextView = findViewById(R.id.timerTextView)
        validateButton = findViewById(R.id.validateButton)
        winnerNameEditText = findViewById(R.id.winnerNameEditText)
        showWinnersButton = findViewById(R.id.showWinnersButton)

        buttons = arrayOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9)
        )

        startTimer()

        var currentNumber = 1
        buttons.forEach { button ->
            button.setOnClickListener {
                button.text = currentNumber.toString()
                currentNumber = if (currentNumber < 9) currentNumber + 1 else 1
            }
        }

        validateButton.setOnClickListener {
            if (isMagicSquare()) {
                val winnerName = winnerNameEditText.text.toString()
                if (winnerName.isNotEmpty()) {
                    val elapsedTime = (System.currentTimeMillis() - startTime) / 1000
                    saveWinner(winnerName, elapsedTime)
                    Toast.makeText(this, "Congratulations $winnerName!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Incorrect solution. Try again!", Toast.LENGTH_SHORT).show()
            }
        }

        showWinnersButton.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            resetGrid()
        }
    }

    private fun saveWinner(name: String, time: Long) {
        val winner = Winner(name = name, timeTaken = time)

        lifecycleScope.launch {
            winnerDatabase.winnerDao().insertWinner(winner)
            Toast.makeText(this@MainActivity, "Winner saved!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTimer() {
        startTime = System.currentTimeMillis()

        val timerHandler = Handler()
        val timerRunnable = object : Runnable {
            override fun run() {
                val elapsedTime = (System.currentTimeMillis() - startTime) / 1000
                timerTextView.text = "Time: $elapsedTime sec"
                timerHandler.postDelayed(this, 1000)
            }
        }
        timerHandler.post(timerRunnable)

    }

    private fun isMagicSquare(): Boolean {
        val inputNumbers = buttons.map { it.text.toString().toIntOrNull() ?: 0 }
        return inputNumbers == correctMagicSquare.toList()
    }

    private fun resetGrid() {
        buttons.forEach { button ->
            button.text = ""
        }
    }
}

