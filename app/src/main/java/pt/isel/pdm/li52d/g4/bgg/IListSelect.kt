package pt.isel.pdm.li52d.g4.bgg

import android.os.Parcelable
import android.widget.Button

interface IListSelect : Parcelable {
    fun selectList(b: Button)
}