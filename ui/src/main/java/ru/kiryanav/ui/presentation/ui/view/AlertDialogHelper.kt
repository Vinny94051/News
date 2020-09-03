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
    private val callback: InputAlertCallback,
    private val title: String,
    private val message: String
) :
    KoinComponent {

    companion object {
        const val ID = 90902
        const val EDIT_TEXT_HEIGHT_OFFSET = 40
    }

    private fun createInputDialog(): AlertDialog {
        val editText = EditText(context).apply {
            id = ID
            setSingleLine()
        }
        return AlertDialog.Builder(context)
            .apply {
                setTitle(title)
                setMessage(message)
                editText.layoutParams = createLayoutParams()
                setView(editText)
                setPositiveButton(context.getString(R.string.Save), null)
            }
            .create()
            //Due to closing alert after pressing positive btn,
            //and you cannot lock btn if editText has less then 3 symbols
            .apply {
                setOnShowListener {
                    getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener {
                            callback.onAlertPositiveSaveClick(editText.text.toString(), this)
                        }
                }
            }
    }

    private fun createLayoutParams() =
        LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            EDIT_TEXT_HEIGHT_OFFSET.toDp()
        )

    fun showAlertDialog() {
        createInputDialog().show()
    }
}