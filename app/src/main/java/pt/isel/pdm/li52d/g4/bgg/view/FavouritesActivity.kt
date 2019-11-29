package pt.isel.pdm.li52d.g4.bgg.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.R

class FavouritesActivity() : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites_list_layout)

        val favouriteList = findViewById<EditText>(R.id.favlistname)
        val publisher = findViewById<EditText>(R.id.publisher)
        val deigner = findViewById<EditText>(R.id.designer)
        val mechanicsButton = findViewById<Button>(R.id.mechanics)
        val categoriesButton = findViewById<Button>(R.id.categories)

        /*
        * These intents are supposed to gather the information about the mechanichs and categories
        * chosen by the user. The objective is the opposite of what we have been doing along the
        * project, instead of we give information about what we want to do in the @OptionsActivity
        * with the method putExtra() we are going to receive the information about what the
        * @OptionsActivity want us to do. To give the information we have to use the putExtra()
        * method in the @OptionsActivity and use the getXXXXExtra() method in this class that will
        * have the options chosen by the user in the @OptionsActivity allowing us to reuse the same
        *  activity to do different tasks
        */
        val mechanicsIntent = Intent(this, OptionsActivity::class.java)
        val categoriesIntent = Intent(this, OptionsActivity::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.fav_list_recycler_view)
    }
}