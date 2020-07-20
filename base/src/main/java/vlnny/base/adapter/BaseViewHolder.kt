package vlnny.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<M>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindView(item: M)

    protected fun <T> setOnViewClickListener(view: View, listener: ((T) -> Unit)?, item: T) =
        view.setOnClickListener {
            listener?.invoke(item)
        }
}