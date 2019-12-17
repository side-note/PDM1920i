package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.li52d.g4.bgg.R
import pt.isel.pdm.li52d.g4.bgg.TIME


class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferences_layout)
        val radioGroup: RadioGroup = findViewById(R.id.preferences_time)
        when(TIME){
            15.toLong() -> radioGroup.check(R.id.min15_button)
            30.toLong() -> radioGroup.check(R.id.min30_button)
            60.toLong() -> radioGroup.check(R.id.hour1_button)
            else -> radioGroup.check(R.id.min15_button)
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
             TIME = when (checkedId) {
                 R.id.min15_button -> 15
                 R.id.min30_button -> 30
                 R.id.hour1_button -> 60
                 else -> 15
            }
        }
    }
}