package ru.kiryanav.ui.presentation.ui.view

import android.content.Context
import android.text.Html
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import ru.kiryanav.ui.R


class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    companion object {
        private const val DEFAULT_TRIM_LENGTH = 200
    }

    private val showMore = "<font color='#6200EE'> ${context.getString(R.string.show_more)}</font>"
    private lateinit var originalText: CharSequence
    private var trimmedText: CharSequence? = null
    private var bufferType: BufferType? = null
    private var trim = true
    private var trimLength: Int = 0
        set(value) {
            field = value
            trimmedText = getTrimmedText()
            setText()
        }


    private fun setText() {
        super.setText(displayableText, bufferType)
    }

    private val displayableText: CharSequence?
        get() = if (trim) trimmedText else originalText

    override fun setText(text: CharSequence, type: BufferType) {
        originalText = text
        trimmedText = getTrimmedText()
        bufferType = type
        setText()
    }

    private fun getTrimmedText(): CharSequence? {
        return if (!::originalText.isInitialized && originalText.length > trimLength) {
            SpannableStringBuilder(
                originalText,
                0,
                trimLength + 1
            ).append("\n").append(Html.fromHtml(showMore))
        } else {
            originalText
        }
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        trimLength = typedArray.getInt(
            R.styleable.ExpandableTextView_trimLength,
            DEFAULT_TRIM_LENGTH
        )
        typedArray.recycle()
        setOnClickListener {
            trim = !trim
            setText()
            requestFocusFromTouch()
        }
    }


}