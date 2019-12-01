package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.*

class MechanicsActivity : AppCompatActivity() {

    val adapter : MechanicsAdapter by lazy {
        MechanicsAdapter(model, intent)
    }

    val model : MechanicsViewModel by lazy {
        val factory = BGGViewModelFactoryProvider(intent)
        ViewModelProviders.of(this, factory)[MechanicsViewModel::class.java]
    }
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options_layout)

        val title = findViewById<TextView>(R.id.options_name)
        title.text = "Mechanics"
        model.observe(this){adapter.notifyDataSetChanged()}
        val recyclerView = findViewById<RecyclerView>(R.id.options_zone)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.options_ok_button).setOnClickListener {
            intent.putExtra(MECHANICSURL, intent.getStringExtra(MECHANICSURL)!!.dropLast(1))
            super.onBackPressed()
        }


    }
}