package pt.isel.pdm.li52d.g4.bgg.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.li52d.g4.bgg.R

class ListsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_lists_layout)

        findViewById<Button>(R.id.list).setOnClickListener{
            startActivity(Intent(this,ListActivity::class.java))
        }
    }
}