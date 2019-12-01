package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.BGGViewModelFactoryProvider
import pt.isel.pdm.li52d.g4.bgg.CategoriesAdapter
import pt.isel.pdm.li52d.g4.bgg.CategoriesViewModel
import pt.isel.pdm.li52d.g4.bgg.R

class CategoriesActivity : AppCompatActivity() {

    val adapter : CategoriesAdapter by lazy {
        CategoriesAdapter(model, intent)
    }

    val model : CategoriesViewModel by lazy {
        val factory = BGGViewModelFactoryProvider(intent)
        ViewModelProviders.of(this, factory)[CategoriesViewModel::class.java]
    }
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options_layout)

        val title = findViewById<TextView>(R.id.options_name)
        title.text = "Categories"
        model.observe(this){adapter.notifyDataSetChanged()}
        val recyclerView = findViewById<RecyclerView>(R.id.options_zone)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }
}