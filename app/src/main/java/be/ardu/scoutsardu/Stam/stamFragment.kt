package be.ardu.scoutsardu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import be.ardu.scoutsardu.databinding.FragmentLoginBinding
import be.ardu.scoutsardu.databinding.FragmentStamBinding

/**
 * A simple [Fragment] subclass.
 */
class stamFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentStamBinding>(inflater, R.layout.fragment_stam, container, false)
        return binding.root
    }


}
