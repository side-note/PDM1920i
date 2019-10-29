package g5.li22d.poo.isel.pt.bgg

import android.app.Application

class GeniuzApp : Application(){
    val bgg by lazy {
        BGGWebApi(applicationContext)
    }
}