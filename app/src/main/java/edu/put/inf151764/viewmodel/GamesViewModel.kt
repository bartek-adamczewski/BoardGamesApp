package edu.put.inf151764.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.put.inf151764.data.db.data.ItemEntity
import edu.put.inf151764.repo.GamesRepo
import edu.put.inf151764.view.data.Game
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: GamesRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(State.DEFAULT)
    val uiState: Flow<State> = _uiState
    private val eventChannel = Channel<Event>(Channel.CONFLATED)
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val data = repository.getItemsFromApi()
            val itemType = savedStateHandle.get<String>("TYPE")
            _uiState.update { state ->
                state.copy(games = data.filter { it.subType == itemType }.map { it.mapToUiModel() })
            }
        }
    }

    data class State(
        val games: List<Game>,
    ) {
        companion object {
            val DEFAULT = State(
                games = emptyList()
            )
        }
    }

    sealed class Event {

    }

    private fun ItemEntity.mapToUiModel() : Game{
        return Game(
            title = this.name,
            id = this.objectId,
            year = this.yearPublished,
            image = this.thumbnail
        )
    }
}