package g5.li22d.poo.isel.pt.bgg

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import g5.li22d.poo.isel.pt.bgg.dto.GameDto
import g5.li22d.poo.isel.pt.bgg.dto.ImageDto
import g5.li22d.poo.isel.pt.bgg.view.DetailedGameInfoActivity
import org.w3c.dom.Text
import java.math.BigDecimal
import java.math.RoundingMode


const val GAME_MBID = "GAME_MBID"
const val GAME_NAME = "GAME_NAME"

class GamesAdapter(val model: GameViewModel) :
    RecyclerView.Adapter<GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        // 1. Obter o TextView i.e. artist_view
        // 2. Inflate parent com o artist_view
        // 3. Instanciar ViewHolder -> passando-lhe o TextView
        val gamesView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_game_layout, parent,false) as ConstraintLayout
        return GameViewHolder(gamesView)
    }

    override fun getItemCount(): Int = model.games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bindTo(model.games[position])
    }
}

class GameViewHolder(private val view: ConstraintLayout) : RecyclerView.ViewHolder(view) {
    private lateinit var game: GameDto
    private val txtGameName: TextView = view.findViewById<TextView>(R.id.gameName)
    private val gameIcon: ImageView = view.findViewById<ImageView>(R.id.game_icon)
    private val ratingNumber: TextView = view.findViewById(R.id.rating_list)
    private val publisher: TextView = view.findViewById(R.id.publisher_list)

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, DetailedGameInfoActivity::class.java)
            intent.putExtra(GAME_NAME, game)
            view.context.startActivity(intent)
        }
    }

    fun bindTo(game: GameDto) {
        this.game = game
        ratingNumber.text = BigDecimal(game.avgUserRating.toString()).setScale(1, RoundingMode.HALF_UP).toDouble().toString()
        publisher.text = game.primaryPublisher
        txtGameName.text = game.name
        Glide.with(view)
            .load(game.images!!.thumb)
            .into(gameIcon)
    }
}