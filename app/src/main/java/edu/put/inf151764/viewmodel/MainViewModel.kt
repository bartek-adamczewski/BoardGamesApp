package edu.put.inf151764.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.put.inf151764.data.api.GamesApi
import edu.put.inf151764.data.api.util.wrapApiCall
import edu.put.inf151764.repo.GamesRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GamesRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(State.DEFAULT)
    val uiState: Flow<State> = _uiState
    private val eventChannel = Channel<Event>(Channel.CONFLATED)
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            if (!repository.isUserLogged()) {
                eventChannel.send(Event.ShowLoginPopup)
            }
        }
        viewModelScope.launch {
            repository.getUser().collect { user ->
                _uiState.update { state ->
                    state.copy(userName = user?.name)
                }
            }
        }
    }

    fun onLogoutClicked() = viewModelScope.launch {
        repository.clearDb()
        eventChannel.send(Event.ExitApp)
    }

    fun onUserPicked(userName: String) = viewModelScope.launch {
        repository.addUser(userName)
    }

    fun onSynchronizeClicked() = viewModelScope.launch {

    }

    data class State(
        val userName: String?,
        val synchronizationDate: LocalDateTime?
    ) {
        companion object {
            val DEFAULT = State(
                userName = null,
                synchronizationDate = null
            )
        }
    }

    sealed class Event {
        object ShowLoginPopup : Event()
        object ExitApp : Event()
    }
}