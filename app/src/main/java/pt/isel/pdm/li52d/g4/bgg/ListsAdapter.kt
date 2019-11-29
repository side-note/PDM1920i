package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames
import pt.isel.pdm.li52d.g4.bgg.model.CustomList

class ListsAdapter(val model: ListViewModel, val intent: Intent) :
    RecyclerView.Adapter<ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        // 1. Obter o TextView i.e. artist_view
        // 2. Inflate parent com o artist_view
        // 3. Instanciar ViewHolder -> passando-lhe o TextView
        val listsView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_lists_layout, parent, false) as ConstraintLayout
        return ListViewHolder(listsView, intent, model)
    }

    override fun getItemCount(): Int = model.lists.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindTo(model.lists[position])
    }
}


class ListViewHolder(view: ConstraintLayout, val intent: Intent, model: ListViewModel) : RecyclerView.ViewHolder(view){
    private lateinit var customList: CustomList
    private val listName: TextView = view.findViewById(R.id.list)
    private val trash: ImageView = view.findViewById(R.id.deleteButton)

    init {
        listName.setOnClickListener {
            intent.getParcelableExtra<IListSelect>(ILIST)!!.selectList(listName.text.toString())
        }
        trash.setOnClickListener {
            /**
             * When trying to delete something from the database it could throw an exception if the
             * thing you're trying to delete is not in the database anymor
             * */
            try {
                BggApp.CUSTOM_LIST_REPO.getGamesList(listName.text.toString()).forEach {
                    BggApp.CUSTOM_LIST_REPO.deleteGamesinList(it.game)
                }
                BggApp.CUSTOM_LIST_REPO.deleteList(BggApp.CUSTOM_LIST_REPO.getList(listName.text.toString()))
                //this will update the reciclerview
                model.getAllLists()
            }catch (e: Exception){ }
        }
    }

    fun bindTo(customList: CustomList) {
        this.customList = customList
        listName.text = customList.name
    }
}