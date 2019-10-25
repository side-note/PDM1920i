package g5.li22d.poo.isel.pt.bgg.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import g5.li22d.poo.isel.pt.bgg.GAME_NAME
import g5.li22d.poo.isel.pt.bgg.R
import g5.li22d.poo.isel.pt.bgg.dto.GameDto
import kotlinx.android.synthetic.main.detailed_info.*


class DetailedGameInfoActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_info)

        val gameDto : GameDto = intent.getParcelableExtra(GAME_NAME)

        val name: TextView =  findViewById(R.id.name_id)
        val description: TextView = findViewById(R.id.game_description)

        Glide.with(this)
            .load(gameDto.images!!.original)
            .into(game_image)
        name.text = gameDto.name
        description.text = gameDto.description

//        name.setOnClickListener{
//            val myIntent = Intent(this, GameListActivity::class.java)
//            myIntent.putExtra(NAME,name.text)
//            startActivity(myIntent)
//        }


    }


}

