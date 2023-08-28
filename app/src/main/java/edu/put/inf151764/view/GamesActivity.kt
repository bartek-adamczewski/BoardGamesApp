package edu.put.inf151764.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import edu.put.inf151764.*
import edu.put.inf151764.view.list.GamesAdapter
import edu.put.inf151764.viewmodel.GamesViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GamesActivity : AppCompatActivity() {

    private val viewModel: GamesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        //recyclerViewGames.adapter = adapter
        //recyclerViewGames.layoutManager = LinearLayoutManager(this)
        val recyclerViewGames = findViewById<RecyclerView>(R.id.gamesRecyclerView)
        val adapter = GamesAdapter(
            onItemClickListener = { game ->
                startActivity(Intent(this, DetailsActivity::class.java).putExtra("ID", game.id))
            }
        )
        recyclerViewGames.adapter = adapter
        recyclerViewGames.layoutManager = LinearLayoutManager(this)

        viewModel.uiState.onEach { state ->
            adapter.submitList(state.games)
        }.launchIn(lifecycleScope)

    }

}