package vlnny.base.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH : BaseViewHolder<MV>, MV> : RecyclerView.Adapter<VH>() {

    private var items: List<MV> = mutableListOf()

    /**
     * Override this fun if you need to bind only new items
     * @param oldItem item which will be replaced
     * @param newItem item which will be inserted
     *
     * @return true if item equals, false if don't
     */
    open fun equals(oldItem: MV, newItem: MV): Boolean =
        oldItem == newItem


    override fun onBindViewHolder(holder: VH, position: Int) = holder.bindView(items[position])

    override fun getItemCount(): Int = items.size

    fun updateList(data: List<MV>) {
        items = data
        notifyDataSetChanged()
    }


    protected fun addItem(item: MV) {
        items.toMutableList().add(item)
        notifyItemChanged(items.size - 1)
    }

    protected fun replaceItem(item: MV, position: Int) {
        val itemsTmp = items.toMutableList()
        itemsTmp[position] = item
        items = itemsTmp

        Log.e(javaClass.simpleName, "$items, : $item")
    }

    protected fun deleteItem(position: Int) {
        items.toMutableList().removeAt(position)
        updateList(items)
    }

    protected fun getItemByPosition(position: Int): MV {
        try {
            return items[position]
        } catch (ex: IndexOutOfBoundsException) {
            ex.printStackTrace()
        }
        return items[0]
    }

}