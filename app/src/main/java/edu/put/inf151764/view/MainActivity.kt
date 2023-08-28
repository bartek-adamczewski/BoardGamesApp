package edu.put.inf151764.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import edu.put.inf151764.viewmodel.MainViewModel
import edu.put.inf151764.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val usernameText by lazy {
        findViewById<TextView>(R.id.nameText)
    }

    private val logoutButton by lazy {
        findViewById<TextView>(R.id.logout_tv)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.uiState.onEach { state ->
            usernameText.text = state.userName ?: "No user"
        }.launchIn(lifecycleScope)

        viewModel.events.onEach {
            when (it) {
                MainViewModel.Event.ShowLoginPopup -> {
                    showLoginPopup()
                }
                MainViewModel.Event.ExitApp -> {
                    this.finish()
                }
            }
        }.launchIn(lifecycleScope)

        val buttonGames = findViewById<ImageButton>(R.id.gamesButton)
        val buttonAddons = findViewById<ImageButton>(R.id.addonsButton)

        buttonGames.setOnClickListener {
            startActivity(Intent(this, GamesActivity::class.java).putExtra("TYPE", "boardgame"))
        }

        buttonAddons.setOnClickListener {
            startActivity(Intent(this, GamesActivity::class.java).putExtra("TYPE", "boardgameexpansion"))
        }

        logoutButton.setOnClickListener {
            viewModel.onLogoutClicked()
        }
    }

    fun showLoginPopup() {
        val popupView: View = layoutInflater.inflate(R.layout.log, null)
        val userNameField: EditText = popupView.findViewById(R.id.editUsername)
        val accept: Button = popupView.findViewById(R.id.okButton)
        val dialogBuilder = AlertDialog.Builder(this).setView(popupView)
        val dialog: AlertDialog = dialogBuilder.create()
        dialog.show()
        accept.setOnClickListener() {
            if (userNameField.text.toString() == "") {
                Toast.makeText(this, "Empty username", Toast.LENGTH_SHORT).show() // nie działa
            } else {

                viewModel.onUserPicked(userName = userNameField.text.toString())
                //text.setText("Nazwa gracza: $userName")
                dialog.dismiss()
                Toast.makeText(this, "Syncing...", Toast.LENGTH_LONG).show()
            }
        }
    }
}