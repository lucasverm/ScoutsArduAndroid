package be.ardu.scoutsardu.stam


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.ardu.scoutsardu.R

/**
 * A simple [Fragment] subclass.
 */
class checkWinkelwagenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_winkelwagen, container, false)
    }


}
