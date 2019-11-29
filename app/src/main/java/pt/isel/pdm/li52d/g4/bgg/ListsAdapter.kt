package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.model.CustomList
import pt.isel.pdm.li52d.g4.bgg.view.AskOption
import pt.isel.pdm.li52d.g4.bgg.view.IDelete

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
        val select = intent.getParcelableExtra<IListSelect>(ILIST)!!
        listName.setOnClickListener {
            select.selectList(listName.text.toString())
        }
        trash.setOnClickListener {
            /**
             * When trying to delete something from the database it could throw an exception if the
             * thing you're trying to delete is not in the database anymore
             * */
            try {
                AskOption.AskDelete(select.ctx!!,DeleteList(model),listName.text.toString())!!.show()
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
class DeleteList(val model: ListViewModel) : IDelete{
    override fun delete(a: Any) {
        BggApp.CUSTOM_LIST_REPO.getGamesAndArtistsList(a as String).forEach {
            BggApp.CUSTOM_LIST_REPO.deleteGamesinList(it.game)
            it.artistList.forEach {
                BggApp.CUSTOM_LIST_REPO.deleteArtist(it)
            }
        }
        BggApp.CUSTOM_LIST_REPO.deleteList(BggApp.CUSTOM_LIST_REPO.getList(a))
        model.getAllLists()
    }

}