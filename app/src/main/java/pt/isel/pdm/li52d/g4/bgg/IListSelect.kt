package pt.isel.pdm.li52d.g4.bgg

import android.content.Context
import android.os.Parcelable
import android.widget.Button
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames

interface IListSelect : Parcelable {
    fun selectList(b: Button)
}