package ru.kiryanav.ui.presentation.ui.view

import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kiryanav.ui.R
import ru.kiryanav.ui.prefs.ISharedPrefsManager
import vlnny.base.ext.toDp

class AlertDialogHelper(private val context: Context) : KoinComponent {
    companion object {
        const val ID = 90902
        const val offset = 40
    }

    private val keyWordPrefs: ISharedPrefsManager by inject()
    val closeLiveData = MutableLiveData<Boolean>()

    private fun createInputDialog(): AlertDialog {
        val editText = EditText(context).apply {
            id = ID
            setSingleLine()
        }
        val dialog = AlertDialog.Builder(context)
            .apply {
                setTitle(context.getString(R.string.alert_title_change_keyword))
                setMessage(context.getString(R.string.alert_comment_change_keyword))
                if (!keyWordPrefs.isKeyWordExist()) {
                    setCancelable(false)
                }
                val lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    offset.toDp()
                )
                editText.layoutParams = lp
                setView(editText)
                setPositiveButton(context.getString(R.string.Save), null)
            }.create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener {
                    val keyWord = editText.text.toString()
                    if (keyWord.length < 3) {
                        Toast.makeText(context, context.getString(R.string.error_change_keyword_short_word), Toast.LENGTH_LONG).show()
                    } else {
                        keyWordPrefs.setKeyWord(keyWord)
                        closeLiveData.value = true
                        dialog.dismiss()
                    }
                }
        }

        return dialog
    }

    fun showAlertDialog() = createInputDialog().show()
}