package pt.isel.pdm.li52d.g4.bgg

import android.app.Application

class BggApp : Application(){
    val bgg by lazy {
        BGGWebApi(applicationContext)
    }
}