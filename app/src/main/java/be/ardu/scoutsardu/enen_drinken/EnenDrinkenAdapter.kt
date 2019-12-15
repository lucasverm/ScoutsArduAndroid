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
import be.ardu.scoutsardu.network.WinkelwagenItemAantal
import be.ardu.scoutsardu.databinding.FragmentEnenDrinkenRecycleviewRowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Exchanger

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class EnenDrinkenAdapter(val clickListener: EnenDrinkenClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(EnenDrinkenDiffCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<WinkelwagenItemAantal>?) {
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
                val winkelwagenItemAantal = getItem(position) as DataItem.WinkelwagenDataItem
                holder.bind(clickListener, winkelwagenItemAantal.winkelwagenItemAantal)
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

        fun fixSubTotaal(winkelwagenItemAantal: WinkelwagenItemAantal){
            val totaal = Math.round(winkelwagenItemAantal.item.prijs * winkelwagenItemAantal.aantal * 100.0) / 100.0
            binding.totaal.setText("€ " + totaal.toString())
        }

        fun bind(clickListener: EnenDrinkenClickListener, winkelwagenItemAantal: WinkelwagenItemAantal) {
            binding.winkelwagenItemAantal = winkelwagenItemAantal
            binding.aantal.addTextChangedListener( object : TextWatcher{

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    try{
                        winkelwagenItemAantal.aantal = Integer.parseInt(s.toString())
                    }catch(e:Exception){
                        winkelwagenItemAantal.aantal = 0
                    }
                    fixSubTotaal(winkelwagenItemAantal)
                }

            })

            if(winkelwagenItemAantal.aantal < 1) {
                binding.min.isEnabled = false
            }else{
                binding.min.isEnabled = true
            }

            binding.min.setOnClickListener {
                winkelwagenItemAantal.verminderrDrank()
                binding.aantal.setText(winkelwagenItemAantal.aantal.toString())
                if(winkelwagenItemAantal.aantal < 1) {
                    it.isEnabled = false
                }
                fixSubTotaal(winkelwagenItemAantal)
            }

            binding.plus.setOnClickListener {
                winkelwagenItemAantal.vermeerderDrank()
                binding.aantal.setText(winkelwagenItemAantal.aantal.toString())
                if(winkelwagenItemAantal.aantal > 0) {
                    binding.min.isEnabled = true
                }
                fixSubTotaal(winkelwagenItemAantal)
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

class EnenDrinkenClickListener(val clickListener: (winkelwagenItemAantal: WinkelwagenItemAantal) -> Unit) {
    fun onClick(winkelwagenItemAantal: WinkelwagenItemAantal) = clickListener(winkelwagenItemAantal)
}

sealed class DataItem {
    data class WinkelwagenDataItem(val winkelwagenItemAantal: WinkelwagenItemAantal) : DataItem() {
        override val id = winkelwagenItemAantal.id
    }

    object Header : DataItem() {
        override val id = 0
    }

    abstract val id: Int
}