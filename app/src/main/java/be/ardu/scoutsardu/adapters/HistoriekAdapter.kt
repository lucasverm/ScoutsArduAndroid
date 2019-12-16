package be.ardu.scoutsardu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.databinding.FragmentHistoriekRecycleviewRowBinding
import be.ardu.scoutsardu.network.Winkelwagen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class HistoriekAdapter(val clickListener: HistoriekClickListener) :
ListAdapter<HistoriekDataItem, RecyclerView.ViewHolder>(HistoryDiffCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Winkelwagen>?) {
        adapterScope.launch {
            val items = when (list?.size) {
                null  -> listOf(HistoriekDataItem.Header)
                0 -> listOf(HistoriekDataItem.Header)
                else -> list.map { HistoriekDataItem.WinkelwagenData(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val winkelwagenData = getItem(position) as HistoriekDataItem.WinkelwagenData
                holder.bind(clickListener, winkelwagenData.winkelwagen)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(
                parent
            )
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(
                parent
            )
            else -> throw ClassCastException("unknow viewtype ${viewType}")
        }
    }

    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.fragment_no_winkelwagen_items_found, parent, false)
                return TextViewHolder(view)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HistoriekDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is HistoriekDataItem.WinkelwagenData -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(val binding: FragmentHistoriekRecycleviewRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: HistoriekClickListener?, winkelwagen: Winkelwagen) {
            binding.winkelwagen = winkelwagen
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentHistoriekRecycleviewRowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class HistoriekClickListener(val clickListener: (winkelwagen: Winkelwagen) -> Unit) {
    fun onClick(winkelwagen: Winkelwagen) = clickListener(winkelwagen)
}

class HistoryDiffCallBack : DiffUtil.ItemCallback<HistoriekDataItem>() {
    override fun areItemsTheSame(oldItem: HistoriekDataItem, newItem: HistoriekDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HistoriekDataItem, newItem: HistoriekDataItem): Boolean {
        return oldItem.equals(newItem)
    }

}


sealed class HistoriekDataItem {
    data class WinkelwagenData(val winkelwagen: Winkelwagen) : HistoriekDataItem() {
        override val id = winkelwagen.id
    }

    object Header : HistoriekDataItem() {
        override val id = 0
    }

    abstract val id: Int
}