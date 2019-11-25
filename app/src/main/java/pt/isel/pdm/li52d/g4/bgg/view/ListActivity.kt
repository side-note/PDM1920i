package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.li52d.g4.bgg.R

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_list)
    }
}