package be.ardu.scoutsardu.historiek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.R.*
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.network.WinkelwagenItem

class HistoriekAdapter(val clickListener: HistoriekClickListener) : RecyclerView.Adapter<HistoriekAdapter.ViewHolder>() {
    var data = listOf<Winkelwagen>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.info.text = item.gebruiker.voornaam + " " +item.gebruiker.achternaam + "(14/12/2019 - 13:13)"
        holder.totaal.text = "€ " + (Math.round(item.getTotaal() * 100.0) / 100.0).toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            layout.fragment_historiek_recycleview_row,
            parent,
            false
        )
        return ViewHolder(view)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val info: TextView = itemView.findViewById(id.info)
        val totaal: TextView = itemView.findViewById(id.totaal)
    }
}

class HistoriekClickListener(val clickListener: (winkelwagen: Winkelwagen) -> Unit) {
    fun onClick(winkelwagen: Winkelwagen) = clickListener(winkelwagen)
}
