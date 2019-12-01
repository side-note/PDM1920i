package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.model.Favorites
import pt.isel.pdm.li52d.g4.bgg.view.AskOption
import pt.isel.pdm.li52d.g4.bgg.view.GameListActivity
import pt.isel.pdm.li52d.g4.bgg.view.IDelete

class FavoritesAdapter(val model: FavoritesViewModel, val intent: Intent) :
    RecyclerView.Adapter<FavoritesListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesListViewHolder {
        // 1. Obter o TextView i.e. artist_view
        // 2. Inflate parent com o artist_view
        // 3. Instanciar ViewHolder -> passando-lhe o TextView
        val favoritesView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_lists_layout, parent, false) as ConstraintLayout
        return FavoritesListViewHolder(favoritesView, intent, model)
    }

    override fun getItemCount(): Int = model.favorites.size

    override fun onBindViewHolder(holder: FavoritesListViewHolder, position: Int) {
        holder.bindTo(model.favorites[position])
    }
}


class FavoritesListViewHolder(view: ConstraintLayout, val intent: Intent, model: FavoritesViewModel) : RecyclerView.ViewHolder(view){
    private lateinit var favList: Favorites
    private val favListName: TextView = view.findViewById(R.id.list)
    private val trash: ImageView = view.findViewById(R.id.deleteButton)

    init {

        favListName.setOnClickListener {
            val myIntent = Intent(model.ctx!!, GameListActivity::class.java )
            myIntent.putExtra(FAVORITE, favListName.text.toString())
            model.ctx!!.startActivity(myIntent)
        }
        trash.setOnClickListener {
            /**
             * When trying to delete something from the database it could throw an exception if the
             * thing you're trying to delete is not in the database anymore
             * */
            try {
                AskOption.askDelete(model.ctx!!, DeleteFavorites(model), "Fav " + favListName.text.toString())!!.show()
                //this will update the reciclerview
                model.getAllFavoritesList()
            }catch (e: Exception){ }
        }
    }

    fun bindTo(favList: Favorites) {
        this.favList = favList
        favListName.text = favList.nameFavList.removePrefix("Fav ")
    }
}
class DeleteFavorites(val model: FavoritesViewModel) : IDelete {
    override fun delete(a: Any) {
        BggApp.CUSTOM_LIST_REPO.getGamesAndDesignersFavList(a as String).forEach {

            BggApp.CUSTOM_LIST_REPO.deleteFavDesigner(it.game.name)
        }
        BggApp.CUSTOM_LIST_REPO.deleteGamesinFav(a)

        BggApp.CUSTOM_LIST_REPO.deleteMechanics(a)

        BggApp.CUSTOM_LIST_REPO.deleteCategories(a)

        BggApp.CUSTOM_LIST_REPO.deleteFavList(a.removePrefix("Fav "))
        model.getAllFavoritesList()
    }

}