package be.ardu.scoutsardu.checkWinkelwagen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.network.WinkelwagenItemAantal

class CheckWinkelwagenAdapter : RecyclerView.Adapter<CheckWinkelwagenAdapter.ViewHolder>() {
    var data = listOf<WinkelwagenItemAantal>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.aantal.text = item.aantal.toString()
        holder.naam.text = item.item.naam
        holder.totaal.text = "€ " + (Math.round(item.item.prijs * item.aantal * 100.0) / 100.0).toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.fragment_check_winkelwagen_recycleview_row,
            parent,
            false
        )
        return ViewHolder(view)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val aantal: TextView = itemView.findViewById(R.id.overzichtAantal)
        val naam: TextView = itemView.findViewById(R.id.overzichtNaam)
        val totaal: TextView = itemView.findViewById(R.id.overzichtTotaal)
    }
}
