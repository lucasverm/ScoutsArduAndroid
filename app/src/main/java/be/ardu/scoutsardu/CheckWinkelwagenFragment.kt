package be.ardu.scoutsardu


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import be.ardu.scoutsardu.databinding.FragmentCheckWinkelwagenBinding
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.adapters.CheckWinkelwagenAdapter
import be.ardu.scoutsardu.viewmodels.CheckWinkelwagenViewModel
import be.ardu.scoutsardu.viewmodels.CheckWinkelwagenViewModelFactory


class CheckWinkelwagenFragment : Fragment() {
    private lateinit var viewModel: CheckWinkelwagenViewModel
    private lateinit var viewModelFactory: CheckWinkelwagenViewModelFactory
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCheckWinkelwagenBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_check_winkelwagen,
                container,
                false
            )

        val adapter = CheckWinkelwagenAdapter()
        binding.overzichtLijst.adapter = adapter


        viewModelFactory = CheckWinkelwagenViewModelFactory()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CheckWinkelwagenViewModel::class.java)
        binding.checkWinkelwagenViewModel = viewModel

        viewModel.winkelwagen.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it.winkelwagenItems.toList()
            }
        })

        arguments?.let {
            viewModel.winkelwagen.value = it.get("winkelwagen") as Winkelwagen
            binding.errorMessage.isVisible = it.get("toonError") as Boolean
        }

        binding.Verder.text = "Totaal: € " + viewModel.winkelwagen.value!!.getTotaal().toString()
        binding.Verder.setOnClickListener {
            val action =
                CheckWinkelwagenFragmentDirections.actionCheckWinkelwagenFragmentToSanteFragment(
                    viewModel.winkelwagen.value!!
                )
            Navigation.findNavController(view!!).navigate(action)
        }

        return binding.root
    }


}
