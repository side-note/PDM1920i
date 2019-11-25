package pt.isel.pdm.li52d.g4.bgg.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import pt.isel.pdm.li52d.g4.bgg.*
import java.io.LineNumberInputStream

class ListsActivity : AppCompatActivity() {
    val model : DetailedGameInfoViewModel by lazy {
        val app = application as BggApp
        val factory = BGGViewModelFactoryProvider(app,intent)
        ViewModelProviders.of(this, factory)[DetailedGameInfoViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_lists_layout)
        val button = findViewById<Button>(R.id.list)
        button.setText("List1")

        findViewById<Button>(R.id.list).setOnClickListener{
            if(intent.getStringExtra(LISTS) != null) {
                val intent = Intent(this, GameListActivity::class.java)
                intent.putExtra(LIST, button.text)
                startActivity(intent)
            } else{
                BggApp.customListRepo.insertGametoCustomList(model.dto,button.text.toString())
                finish()
            }
        }
    }
}