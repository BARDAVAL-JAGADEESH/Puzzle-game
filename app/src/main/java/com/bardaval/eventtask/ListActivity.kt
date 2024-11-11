package com.bardaval.eventtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {

    private lateinit var winnersRecyclerView: RecyclerView
    private lateinit var winnerDatabase: WinnerDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        winnerDatabase = WinnerDatabase.getDatabase(this)
        winnersRecyclerView = findViewById(R.id.winnersRecyclerView)

        winnersRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val winners = winnerDatabase.winnerDao().getAllWinners()
            winnersRecyclerView.adapter = WinnerAdapter(winners)
        }
    }
}
