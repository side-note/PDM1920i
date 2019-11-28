package pt.isel.pdm.li52d.g4.bgg

import android.app.Activity
import android.content.Context
import android.os.Parcelable
import android.widget.Button
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames

interface IListSelect : Parcelable {
    var ctx: Context?
    var act: Activity?
    var artistsAndGames: ArtistsAndGames?
    fun selectList(listName: String)
}