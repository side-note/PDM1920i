package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames
import pt.isel.pdm.li52d.g4.bgg.model.CustomList
import pt.isel.pdm.li52d.g4.bgg.view.GameListActivity

class ListsAdapter(val model: ListViewModel) :
    RecyclerView.Adapter<ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        // 1. Obter o TextView i.e. artist_view
        // 2. Inflate parent com o artist_view
        // 3. Instanciar ViewHolder -> passando-lhe o TextView
        val listsView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_game_layout, parent, false) as ConstraintLayout
        return ListViewHolder(listsView)
    }

    override fun getItemCount(): Int = model.lists.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindTo(model.lists[position])
    }
}


class ListViewHolder(private val view: ConstraintLayout) : RecyclerView.ViewHolder(view){
    private lateinit var customList: CustomList
    private val listButton: TextView = view.findViewById(R.id.list)

    init {
        view.setOnClickListener {
//            if(intent.getStringExtra(LISTS) != null) {
                val intent = Intent(view.context, GameListActivity::class.java)
                intent.putExtra(LIST, listButton.text)
                view.context.startActivity(intent)
//            } else{
//                val artistsAndGames = intent.getParcelableExtra(GAME_NAME)
//                artistsAndGames!!.game.nameList = listButton.text.toString()
//                BggApp.CUSTOM_LIST_REPO.insertGame(artistsAndGames.game)
//                artistsAndGames.artistList.forEach {
//                    BggApp.CUSTOM_LIST_REPO.insertArtist(artistsAndGames.game.name, it.artistName)
//                }
//                super.onBackPressed()
//            }
        }
    }

    fun bindTo(customList: CustomList) {
        this.customList = customList
        listButton.text = customList.name
    }
}