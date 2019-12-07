package be.ardu.scoutsardu.checkWinkelwagen


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.databinding.FragmentCheckWinkelwagenBinding
import be.ardu.scoutsardu.databinding.FragmentEnenDrinkenBinding
import be.ardu.scoutsardu.enen_drinken.EnenDrinkenViewModel
import be.ardu.scoutsardu.enen_drinken.EnenDrinkenViewModelFactory
import be.ardu.scoutsardu.models.Winkelwagen

/**
 * A simple [Fragment] subclass.
 */
class checkWinkelwagenFragment : Fragment() {
    private lateinit var viewModel: CheckWinkelwagenViewModel
    private lateinit var viewModelFactory: CheckWinkelwagenViewModelFactory
    private var data: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCheckWinkelwagenBinding = DataBindingUtil.inflate<FragmentCheckWinkelwagenBinding>(
            inflater,
            R.layout.fragment_check_winkelwagen,
            container,
            false
        )
        val adapter = checkWinkelwagenAdapter()
        binding.overzichtLijst.adapter = adapter
        viewModelFactory = CheckWinkelwagenViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CheckWinkelwagenViewModel::class.java)
        binding.checkWinkelwagenViewModel = viewModel

        viewModel.winkelwagen.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it?.winkelwagenItems.toList()
            }
        })

        arguments?.let {
            viewModel.winkelwagen.value = it.get("winkelwagen") as Winkelwagen
        }





        return binding.root
    }


}
