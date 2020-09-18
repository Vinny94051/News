package vlnny.base.adapter

import java.lang.ClassCastException


class MultiItemRecyclerViewHolderCreator<M>(
    private val holders: HashMap<Int, BaseViewHolder<*>>
) {
    @Suppress("UNCHECKED_CAST")
    fun onCreateViewHolder(viewType: Int): BaseViewHolder<M> {
        if (holders[viewType] is BaseViewHolder) {
            return holders[viewType] as BaseViewHolder<M>
        } else {
            throw ClassCastException("No such class")
        }
    }
}