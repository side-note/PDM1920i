package pt.isel.pdm.li52d.g4.bgg.view

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import java.util.*


class AskOption() {
    companion object {
        fun AskDelete(ctx: Context, del: IDelete, a: Any): AlertDialog? {
            return AlertDialog.Builder(ctx) // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Are you sure you want to Delete?")

                .setPositiveButton("Delete"
                ) { dialog, whichButton ->
                    del.delete(a)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel"
                ) { dialog, which -> dialog.dismiss() }
                .create()
        }
    }
}

interface IDelete {
    fun delete(a: Any)
}
