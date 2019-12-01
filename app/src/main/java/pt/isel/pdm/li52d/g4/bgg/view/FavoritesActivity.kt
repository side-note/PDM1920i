package pt.isel.pdm.li52d.g4.bgg.view

import android.app.Activity
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

    private var mechanicsUrl: String = ""
    private var mechanicsNames: String = ""
    private var categoriesUrl: String = ""
    private var categoriesNames: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites_list_layout)

        val favoriteList = findViewById<EditText>(R.id.favlistname)
        favoriteList.hint = "List Name"
        val publisher = findViewById<EditText>(R.id.publisher)
        publisher.hint = "Publisher"
        val designer = findViewById<EditText>(R.id.designer)
        designer.hint = "Designer"
        val mechanicsButton = findViewById<Button>(R.id.mechanics)
        val categoriesButton = findViewById<Button>(R.id.categories)
        val addButton = findViewById<Button>(R.id.add_button)
        model.ctx = this
        val intentM = Intent(this, MechanicsActivity::class.java)
        val intentC = Intent(this, CategoriesActivity::class.java)
        intentM.putExtra(MECHANICSURL, "")
        intentM.putExtra(MECHANICSNAMES, "")
        intentC.putExtra(CATEGORIESURL, "")
        intentC.putExtra(CATEGORIESNAMES, "")
        model.observe(this){adapter.notifyDataSetChanged()}
        val recyclerView = findViewById<RecyclerView>(R.id.fav_list_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        addButton.setOnClickListener{
            model.favoriteSearch(
                "Fav " + favoriteList.text.toString(),
                publisher.text.toString(),
                designer.text.toString(),
                mechanicsUrl,
                categoriesUrl,
                mechanicsNames,
                categoriesNames
            )
            BggApp.CUSTOM_LIST_REPO.insertFavorite(favoriteList.text.toString(), publisher.text.toString(), designer.text.toString())
            model.getAllFavoritesList()
            mechanicsUrl = ""
            mechanicsNames = ""
            categoriesUrl = ""
            categoriesNames = ""

        }

        mechanicsButton.setOnClickListener{
            intentM.putExtra(MECHANICS, "mechanics")
            startActivityForResult(intentM, MECHANICSCODE)
        }

        categoriesButton.setOnClickListener {
            intentC.putExtra(CATEGORIES, "categories")
            startActivityForResult(intentC, CATEGORIESCODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            MECHANICSCODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    mechanicsUrl = data!!.getStringExtra(MECHANICSURL)!!
                    mechanicsNames = data.getStringExtra(MECHANICSNAMES)!!
                }
            }
            CATEGORIESCODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    categoriesUrl = data!!.getStringExtra(CATEGORIESURL)!!
                    categoriesNames = data.getStringExtra(CATEGORIESNAMES)!!
                }
            }
        }
    }

}
