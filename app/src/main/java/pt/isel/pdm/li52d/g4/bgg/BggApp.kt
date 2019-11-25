package pt.isel.pdm.li52d.g4.bgg

import android.app.Application
import androidx.room.Room
import pt.isel.pdm.li52d.g4.bgg.model.BGGDb
import pt.isel.pdm.li52d.g4.bgg.model.CustomListGameJoinRepository

class BggApp : Application(){
    lateinit var bgg: BGGWebApi
    companion object {
        val customListRepo: CustomListGameJoinRepository by lazy { CustomListGameJoinRepository() }
        lateinit var db: BGGDb
    }

    override fun onCreate() {
        super.onCreate()
        bgg = BGGWebApi(applicationContext)
        db = Room
                 .databaseBuilder(applicationContext, BGGDb::class.java, "bgg-db" )
                 .build()
    }
}

