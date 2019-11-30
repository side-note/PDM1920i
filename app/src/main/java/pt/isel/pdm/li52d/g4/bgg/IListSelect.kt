package pt.isel.pdm.li52d.g4.bgg

import android.app.Activity
import android.content.Context
import android.os.Parcelable
import pt.isel.pdm.li52d.g4.bgg.model.DesignersAndGames

interface IListSelect : Parcelable {
    var ctx: Context?
    var act: Activity?
    var designersAndGames: DesignersAndGames?
    fun selectList(listName: String)
}