package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import pt.isel.pdm.li52d.g4.bgg.R
import pt.isel.pdm.li52d.g4.bgg.TIME


class PreferencesActivity : AppCompatActivity() {
    private var time : Long = TIME
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferences_layout)
        val radioGroup: RadioGroup = findViewById(R.id.preferences_time)
        val save: MaterialButton = findViewById(R.id.save_button)
        when(TIME){
            15.toLong() -> radioGroup.check(R.id.min15_button)
            30.toLong() -> radioGroup.check(R.id.min30_button)
            60.toLong() -> radioGroup.check(R.id.hour1_button)
            else -> radioGroup.check(R.id.min15_button)
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
             time = when (checkedId) {
                 R.id.min15_button -> 15
                 R.id.min30_button -> 30
                 R.id.hour1_button -> 60
                 else -> 15
            }
        }
        save.setOnClickListener {
            TIME = time
            finish()
        }
    }
}