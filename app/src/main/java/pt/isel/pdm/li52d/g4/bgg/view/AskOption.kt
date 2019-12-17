package pt.isel.pdm.li52d.g4.bgg.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import pt.isel.pdm.li52d.g4.bgg.R






class AskOption(val ctx: Context, val del: IDelete, val a: Any) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        return AlertDialog.Builder(ctx) // set message and title
            .setTitle(ctx.resources.getString(R.string.delete))
            .setMessage(ctx.resources.getString(R.string.delete_question))
            .setPositiveButton(ctx.resources.getString(R.string.delete)) {
                    dialog, _ ->
                del.delete(a)
                dialog.dismiss()
            }
            .setNegativeButton(ctx.resources.getString(R.string.cancel)) {
                    dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }


//    fun askDelete(): AlertDialog {
//        return AlertDialog.Builder(ctx) // set message and title
//                .setTitle(ctx.resources.getString(R.string.delete))
//                .setMessage(ctx.resources.getString(R.string.delete_question))
//                .setPositiveButton(ctx.resources.getString(R.string.delete)) {
//                    dialog, whichButton ->
//                        del.delete(a)
//                        dialog.dismiss()
//                }
//                .setNegativeButton(ctx.resources.getString(R.string.cancel)) {
//                    dialog, which ->
//                    dialog.dismiss()
//                }
//                .create()
//    }

}

interface IDelete {
    fun delete(a: Any)
}
