package be.ardu.scoutsardu.enen_drinken

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.network.WinkelwagenItem
import be.ardu.scoutsardu.databinding.FragmentEnenDrinkenRecycleviewRowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class EnenDrinkenAdapter(val clickListener: EnenDrinkenClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(EnenDrinkenDiffCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<WinkelwagenItem>?) {
        adapterScope.launch {
            val items = when (list?.size) {
                null  -> listOf(DataItem.Header)
                0 -> listOf(DataItem.Header)
                else -> list.map { DataItem.WinkelwagenDataItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val winkelwagenItem = getItem(position) as DataItem.WinkelwagenDataItem
                holder.bind(clickListener, winkelwagenItem.winkelwagenItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
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
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.WinkelwagenDataItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(val binding: FragmentEnenDrinkenRecycleviewRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun fixSubTotaal(winkelwagenItem: WinkelwagenItem){
            val totaal = Math.round(winkelwagenItem.prijs * winkelwagenItem.aantal * 100.0) / 100.0
            binding.totaal.setText(totaal.toString())
        }

        fun bind(clickListener: EnenDrinkenClickListener, winkelwagenItem: WinkelwagenItem) {
            binding.winkelwagenItem = winkelwagenItem
            binding.aantal.addTextChangedListener( object : TextWatcher{

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    winkelwagenItem.aantal = Integer.parseInt(s.toString())
                    fixSubTotaal(winkelwagenItem)
                }

            })

            if(winkelwagenItem.aantal < 1) {
                binding.min.isEnabled = false
            }else{
                binding.min.isEnabled = true
            }

            binding.min.setOnClickListener {
                winkelwagenItem.verminderrDrank()
                binding.aantal.setText(winkelwagenItem.aantal.toString())
                if(winkelwagenItem.aantal < 1) {
                    it.isEnabled = false
                }
                fixSubTotaal(winkelwagenItem)
            }

            binding.plus.setOnClickListener {
                winkelwagenItem.vermeerderDrank()
                binding.aantal.setText(winkelwagenItem.aantal.toString())
                if(winkelwagenItem.aantal > 0) {
                    binding.min.isEnabled = true
                }
                fixSubTotaal(winkelwagenItem)
            }
            //binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentEnenDrinkenRecycleviewRowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class EnenDrinkenDiffCallBack : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.equals(newItem)
    }

}

class EnenDrinkenClickListener(val clickListener: (winkelwagenItem: WinkelwagenItem) -> Unit) {
    fun onClick(winkelwagenItem: WinkelwagenItem) = clickListener(winkelwagenItem)
}

sealed class DataItem {
    data class WinkelwagenDataItem(val winkelwagenItem: WinkelwagenItem) : DataItem() {
        override val id = winkelwagenItem.id
    }

    object Header : DataItem() {
        override val id = 0
    }

    abstract val id: Int
}