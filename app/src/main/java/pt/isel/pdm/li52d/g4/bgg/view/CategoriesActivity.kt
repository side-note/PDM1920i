package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.li52d.g4.bgg.R

class CategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mechanics_layout)

        val title = findViewById<TextView>(R.id.options)
        val confirmButton = findViewById<Button>(R.id.options_ok_button)
        //Somehow we are suppose to put the switches in the LinearLayout below.
        // Maybe with the addView() method
        val optionsZone = findViewById<LinearLayout>(R.id.options_zone)

    }
}
