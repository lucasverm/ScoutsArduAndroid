package be.ardu.scoutsardu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import be.ardu.scoutsardu.databinding.FragmentEnenDrinkenBinding

/**
 * A simple [Fragment] subclass.
 */
class EnenDrinkenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentEnenDrinkenBinding>(inflater, R.layout.fragment_enen_drinken, container, false)
        return binding.root
    }


}
