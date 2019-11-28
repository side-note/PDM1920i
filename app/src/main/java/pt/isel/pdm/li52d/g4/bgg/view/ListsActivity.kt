package pt.isel.pdm.li52d.g4.bgg.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.games_list_layout.*
import pt.isel.pdm.li52d.g4.bgg.*
import pt.isel.pdm.li52d.g4.bgg.dto.SearchDto
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames

class ListsActivity : AppCompatActivity(){

    val adapter : ListsAdapter by lazy {
        ListsAdapter(model, intent)
    }
    val model : ListViewModel by lazy {
        val factory = BGGViewModelFactoryProvider(intent)
        ViewModelProviders.of(this, factory)[ListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_list_layout)
//        val button = findViewById<Button>(R.id.list)
//        val listName = "List1"
        val listName = findViewById<EditText>(R.id.editText)

        model.observe(this){adapter.notifyDataSetChanged()}
        val recyclerView =findViewById<RecyclerView>(R.id.recycler_view_lists)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.addList).setOnClickListener {
            BggApp.CUSTOM_LIST_REPO.insertList(listName.text.toString())
            recyclerView.invalidate()
        }
    }
}