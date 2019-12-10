package pt.isel.pdm.li52d.g4.bgg.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorites_list_layout.*
import pt.isel.pdm.li52d.g4.bgg.*

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
        title.text = resources.getText(R.string.categories)
        model.observe(this){adapter.notifyDataSetChanged()}
        val recyclerView = findViewById<RecyclerView>(R.id.options_zone)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.options_ok_button).setOnClickListener {
            val categoriesUrl = intent.getStringExtra(CATEGORIESURL)!!.dropLast(1)
            val categoriesNames = intent.getStringExtra(CATEGORIESNAMES)!!.dropLast(1)
            val intentResult = Intent()
            intentResult.putExtra(CATEGORIESURL, categoriesUrl)
            intentResult.putExtra(CATEGORIESNAMES, categoriesNames)
            setResult(Activity.RESULT_OK, intentResult)
            super.onBackPressed()
        }
    }
}