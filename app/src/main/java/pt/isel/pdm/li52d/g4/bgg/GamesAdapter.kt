package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pt.isel.pdm.li52d.g4.bgg.model.DesignersAndGames
import pt.isel.pdm.li52d.g4.bgg.model.Game
import pt.isel.pdm.li52d.g4.bgg.view.AskOption
import pt.isel.pdm.li52d.g4.bgg.view.DetailedGameInfoActivity
import pt.isel.pdm.li52d.g4.bgg.view.IDelete
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode

const val GAME_NAME = "GAME_NAME"

class GamesAdapter(val model: GameViewModel, val intent: Intent) :
    RecyclerView.Adapter<GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val gamesView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_game_layout, parent, false) as ConstraintLayout
        return GameViewHolder(gamesView, model, intent)
    }

    override fun getItemCount(): Int {
        return if(model.games.size > 30 && !model.name.contains("List ") && !model.name.contains("Fav ")) model.games.size - 1 else model.games.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        if(position < itemCount)
            holder.bindTo(model.games[position])
    }
}

class GameViewHolder(private val view: ConstraintLayout, model: GameViewModel, intent: Intent) : RecyclerView.ViewHolder(view) {
    private lateinit var designersAndGames: DesignersAndGames
    private val txtGameName: TextView = view.findViewById(R.id.gameName)
    private val gameIcon: ImageView = view.findViewById(R.id.game_icon)
    private val ratingNumber: TextView = view.findViewById(R.id.rating_list)
    private val publisher: TextView = view.findViewById(R.id.publisher_list)
    private val deleteGame: ImageView = view.findViewById(R.id.delete_game)

    init {
        if(intent.getBooleanExtra("trashCan", true))
            deleteGame.visibility = ImageView.INVISIBLE
        view.setOnClickListener {
            val myIntent = Intent(view.context, DetailedGameInfoActivity::class.java)
            myIntent.putExtra(GAME_NAME, designersAndGames)
            view.context.startActivity(myIntent)
        }
        deleteGame.setOnClickListener {
            try {
                AskOption.askDelete(model.ctx!!, DeleteGame(model,intent.getStringExtra(LIST)!!), designersAndGames.game)!!.show()
            } catch (e: Exception){ }
        }
    }

    fun bindTo(designersAndGames: DesignersAndGames) {
        this.designersAndGames = designersAndGames
        ratingNumber.text = BigDecimal(designersAndGames.game.rating.toString()).setScale(1, RoundingMode.HALF_UP).toDouble().toString()
        publisher.text = designersAndGames.game.publisher
        txtGameName.text = designersAndGames.game.name
        Glide.with(view)
            .load(designersAndGames.game.smallImage)
            .into(gameIcon)
    }
}

class DeleteGame(val model: GameViewModel, private val s: String) : IDelete{
    override fun delete(a: Any) {
        BggApp.CUSTOM_LIST_REPO.deleteGamesinList(a as Game)
        model.getList(s)
    }

}