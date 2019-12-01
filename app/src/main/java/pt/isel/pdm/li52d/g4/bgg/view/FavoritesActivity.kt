package pt.isel.pdm.li52d.g4.bgg.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.*

class FavoritesActivity : AppCompatActivity(){

    val adapter : FavoritesAdapter by lazy {
        FavoritesAdapter(model, intent)
    }
    val model : FavoritesViewModel by lazy{
        val factory = BGGViewModelFactoryProvider(intent)
        ViewModelProviders.of(this, factory)[FavoritesViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites_list_layout)

        val favoriteList = findViewById<EditText>(R.id.favlistname)
        val publisher = findViewById<EditText>(R.id.publisher)
        val designer = findViewById<EditText>(R.id.designer)
        val mechanicsButton = findViewById<Button>(R.id.mechanics)
        val categoriesButton = findViewById<Button>(R.id.categories)
        val addButton = findViewById<Button>(R.id.add_button)
        val intentM = Intent(this, MechanicsActivity::class.java)
        val intentC = Intent(this, CategoriesActivity::class.java)
        intentM.putExtra(MECHANICSURL, "")
        intentM.putExtra(MECHANICSNAMES, "")
        intentC.putExtra(CATEGORIESURL, "")
        intentC.putExtra(CATEGORIESNAMES, "")

        val recyclerView = findViewById<RecyclerView>(R.id.fav_list_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        model.observe(this){adapter.notifyDataSetChanged()}

        addButton.setOnClickListener{
            model.favoriteSearch(
                favoriteList.text.toString(),
                publisher.text.toString(),
                designer.text.toString(),
                intentM.getStringExtra(MECHANICSURL)!!,
                intentC.getStringExtra(CATEGORIESURL)!!,
                intentM.getStringExtra(MECHANICSNAMES)!!,
                intentC.getStringExtra(CATEGORIESNAMES)!!
            )
            BggApp.CUSTOM_LIST_REPO.insertFavorite(favoriteList.text.toString(), publisher.text.toString(), designer.text.toString())
            model.getAllFavoritesList()
        }

        mechanicsButton.setOnClickListener{
            intentM.putExtra(MECHANICS, "mechanics")
            startActivity(intentM)
        }

        categoriesButton.setOnClickListener {
            intentC.putExtra(CATEGORIES, "categories")
            startActivity(intentC)
        }
    }

}
