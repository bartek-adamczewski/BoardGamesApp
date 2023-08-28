package edu.put.inf151764.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import edu.put.inf151764.R
import edu.put.inf151764.viewmodel.DetailsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    private val titleDetail: TextView by lazy {
        findViewById(R.id.detailTitle)
    }

    private val image: ImageView by lazy {
        findViewById(R.id.detailImage)
    }

    private val rankDetail: TextView by lazy {
        findViewById(R.id.detailRank)
    }

    private val yearDetail: TextView by lazy {
        findViewById(R.id.detailYear)
    }

    private val descriptionDetail: TextView by lazy {
        findViewById(R.id.detailDescription)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewModel.uiState.onEach { state ->
            titleDetail.text = state.game?.title.orEmpty()
            rankDetail.text = "Rank: ${state.game?.rank.orEmpty()}"
            yearDetail.text = "Year: ${state.game?.year.orEmpty()}"
            descriptionDetail.text = "${state.game?.description}"
            Picasso.get().load(state.game?.image).into(image)

        }.launchIn(lifecycleScope)
    }
}