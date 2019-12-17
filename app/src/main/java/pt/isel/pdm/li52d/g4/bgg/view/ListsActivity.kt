package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.*
import pt.isel.pdm.li52d.g4.bgg.model.DesignersAndGames

class ListsActivity : AppCompatActivity(){

    val adapter : ListsAdapter by lazy {
        ListsAdapter(model, intent, this)
    }
    val model : ListViewModel by lazy {
        val factory = BGGViewModelFactoryProvider(intent)
        ViewModelProviders.of(this, factory)[ListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_list_layout)

        val select: IListSelect = intent.getParcelableExtra(ILIST)!!
        val designersAndGames = intent.getParcelableExtra<DesignersAndGames>(FROM_DETAILED_ACTIVITY)
        select.act = this
        select.ctx = this
        select.designersAndGames = designersAndGames
        intent.putExtra(ILIST, select)

        val listName = findViewById<EditText>(R.id.editText)
        listName.hint = "Name of the List"
        model.observe(this){adapter.notifyDataSetChanged()}
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_lists)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.addList).setOnClickListener {
            val name = listName.text.toString()
            if(name.length > 0) {
                BggApp.CUSTOM_LIST_REPO.insertList(name)
                //this will update the reciclerview
                model.getAllLists()
            }
        }
    }
}