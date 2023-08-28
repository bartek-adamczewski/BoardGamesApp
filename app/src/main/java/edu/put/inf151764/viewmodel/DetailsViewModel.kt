package edu.put.inf151764.viewmodel

import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.put.inf151764.data.api.data.details.DetailsResponse
import edu.put.inf151764.data.api.util.GamesApiResult
import edu.put.inf151764.repo.GamesRepo
import edu.put.inf151764.view.data.DetailGame
import edu.put.inf151764.view.data.Game
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: GamesRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(State.DEFAULT)
    val uiState: Flow<State> = _uiState
    private val eventChannel = Channel<Event>(Channel.CONFLATED)
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>("ID")!!
            val resp = repository.getDetails(id)
            when(resp) {
                is GamesApiResult.Exception -> {
                    println("Error")
                }
                is GamesApiResult.Success -> {
                    _uiState.update { state ->
                        state.copy(game = resp.data.toUiModel())
                    }
                }
            }
        }
    }

    private fun DetailsResponse.toUiModel(): DetailGame {
        val item = this.items.first()
        return DetailGame(
            image = item.image,
            title = item.names.first { it.type == "primary" }.value,
            description = item.description,
            year = item.yearPublished.value,
            min = item.minPlayers.value,
            max = item.maxPlayers.value,
            rank = item.statistics.ratings.ranks.firstOrNull()?.value
        )
    }

    data class State(
        val game: DetailGame?,
    ) {
        companion object {
            val DEFAULT = State(
                game = null
            )
        }
    }

    sealed class Event {

    }
}