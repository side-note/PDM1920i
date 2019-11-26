package pt.isel.pdm.li52d.g4.bgg

import android.app.Application
import androidx.room.Room
import pt.isel.pdm.li52d.g4.bgg.model.BGGDb
import pt.isel.pdm.li52d.g4.bgg.model.BGGRepository

class BggApp : Application(){
    companion object {
        val CUSTOM_LIST_REPO: BGGRepository by lazy { BGGRepository() }
        lateinit var db: BGGDb
        lateinit var bgg: BGGWebApi
    }

    override fun onCreate() {
        super.onCreate()
        bgg = BGGWebApi(applicationContext)
        db = Room
                 .databaseBuilder(applicationContext, BGGDb::class.java, "bgg-db" )
                 .build()
    }
}

