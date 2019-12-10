package pt.isel.pdm.li52d.g4.bgg.view

import android.app.AlertDialog
import android.content.Context
import pt.isel.pdm.li52d.g4.bgg.R


class AskOption {
    companion object {
        fun askDelete(ctx: Context, del: IDelete, a: Any): AlertDialog {
            return AlertDialog.Builder(ctx) // set message and title
                    .setTitle(ctx.resources.getString(R.string.delete))
                    .setMessage(ctx.resources.getString(R.string.delete_question))
                    .setPositiveButton(ctx.resources.getString(R.string.delete)) {
                        dialog, whichButton ->
                            del.delete(a)
                            dialog.dismiss()
                    }
                    .setNegativeButton(ctx.resources.getString(R.string.cancel)) {
                        dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
        }
    }
}

interface IDelete {
    fun delete(a: Any)
}
