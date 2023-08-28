package edu.put.inf151764.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.put.inf151764.R
import edu.put.inf151764.view.data.Game
import edu.put.inf151764.viewmodel.GamesViewModel


class GamesAdapter(private val onItemClickListener: (Game) -> Unit) : androidx.recyclerview.widget.ListAdapter<Game, GamesAdapter.GameViewHolder>(GameDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_recycler_view_layout, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)
        holder.bind(game)
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.gamesTitle)
        private val id = itemView.findViewById<TextView>(R.id.gamesId)
        private val year = itemView.findViewById<TextView>(R.id.gamesYear)
        private val image = itemView.findViewById<ImageView>(R.id.gamesImage)

        private lateinit var model : Game

        init {
            itemView.setOnClickListener {
                onItemClickListener(model)
            }
        }
        fun bind(game: Game) {
            model = game
            title.text = "${game.title}"
            id.text = "ID: ${game.id}"
            year.text = "YEAR: ${game.year}"
            Picasso.get().load(game.image).into(image)
        }
    }

    class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }

}