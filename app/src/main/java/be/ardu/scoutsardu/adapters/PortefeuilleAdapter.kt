package be.ardu.scoutsardu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.databinding.FragmentPortefeuilleRecycleviewRowBinding
import be.ardu.scoutsardu.network.Winkelwagen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class PortefeuilleAdapter(val clickListener: PortefeuilleClickListener) :
    ListAdapter<PortefeuilleDataItem, RecyclerView.ViewHolder>(PortefeuilleDiffCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Winkelwagen>?) {
        adapterScope.launch {
            val items = when (list?.size) {
                null  -> listOf(PortefeuilleDataItem.Header)
                0 -> listOf(PortefeuilleDataItem.Header)
                else -> list.map { PortefeuilleDataItem.WinkelwagenData(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val winkelwagenData = getItem(position) as PortefeuilleDataItem.WinkelwagenData
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
            else -> throw ClassCastException("unknow viewtype $viewType")
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
            is PortefeuilleDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is PortefeuilleDataItem.WinkelwagenData -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(val binding: FragmentPortefeuilleRecycleviewRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: PortefeuilleClickListener?, winkelwagen: Winkelwagen) {
            binding.winkelwagen = winkelwagen
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentPortefeuilleRecycleviewRowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class PortefeuilleClickListener(val clickListener: (winkelwagen: Winkelwagen) -> Unit) {
    fun onClick(winkelwagen: Winkelwagen) = clickListener(winkelwagen)
}

class PortefeuilleDiffCallBack : DiffUtil.ItemCallback<PortefeuilleDataItem>() {
    override fun areItemsTheSame(oldItem: PortefeuilleDataItem, newItem: PortefeuilleDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PortefeuilleDataItem, newItem: PortefeuilleDataItem): Boolean {
        return oldItem.equals(newItem)
    }

}


sealed class PortefeuilleDataItem {
    data class WinkelwagenData(val winkelwagen: Winkelwagen) : PortefeuilleDataItem() {
        override val id = winkelwagen.id
    }

    object Header : PortefeuilleDataItem() {
        override val id = 0
    }

    abstract val id: Int
}