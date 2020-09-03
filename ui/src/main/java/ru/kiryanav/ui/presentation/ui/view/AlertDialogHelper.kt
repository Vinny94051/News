package ru.kiryanav.ui.presentation.ui.view

import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import org.koin.core.KoinComponent
import ru.kiryanav.ui.R
import vlnny.base.ext.toDp

class AlertDialogHelper(
    private val context: Context,
    private val callback: InputAlertCallback
) :
    KoinComponent {

    companion object {
        const val ID = 90902
        const val OFFSET = 40
    }

    private lateinit var alertDialog: AlertDialog

    private fun createInputDialog() {
        val editText = EditText(context).apply {
            id = ID
            setSingleLine()
        }
        alertDialog = AlertDialog.Builder(context)
            .apply {
                setTitle(context.getString(R.string.alert_title_change_keyword))
                setMessage(context.getString(R.string.alert_comment_change_keyword))
                editText.layoutParams = createLayoutParams()
                setView(editText)
                setPositiveButton(context.getString(R.string.Save), null)
            }
            .create()
            //Due to closing alert after pressing positive btn,
            //and you cannot lock btn if editText has less then 3 symbols
            .apply {
                setOnShowListener {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener {
                            callback.onAlertPositiveSaveClick(editText.text.toString(), alertDialog)
                        }
                }
            }
    }

    private fun createLayoutParams() =
        LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            OFFSET.toDp()
        )


    fun showAlertDialog() {
        if (!::alertDialog.isInitialized) {
            createInputDialog()
        }
        alertDialog.show()
    }
}