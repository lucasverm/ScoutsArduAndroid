package be.ardu.scoutsardu.enen_drinken


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.databinding.FragmentEnenDrinkenBinding
import be.ardu.scoutsardu.network.Winkelwagen
import android.widget.TextView
import android.graphics.Color
import be.ardu.scoutsardu.Repositories.AccountRepository
import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.ScoutsArduApiStatus


/**
 * A simple [Fragment] subclass.
 */
class EnenDrinkenFragment : Fragment() {

    private lateinit var viewModel: EnenDrinkenViewModel
    private lateinit var viewModelFactory: EnenDrinkenViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentEnenDrinkenBinding =
            DataBindingUtil.inflate<FragmentEnenDrinkenBinding>(
                inflater,
                R.layout.fragment_enen_drinken,
                container,
                false
            )

        //viewmodel initialisatie voor het fragment
        viewModelFactory = EnenDrinkenViewModelFactory()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EnenDrinkenViewModel::class.java)
        binding.enenDrinkenViewModel = viewModel

        //grid layout settings
        val manager = GridLayoutManager(activity, 1)
        /*manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (viewModel.items.value?.size) {
                null -> 3
                0 -> 3
                else -> 1
            }
        }*/
        binding.winkelwagenItemsRecycleView.layoutManager = manager

        binding.setLifecycleOwner(this)

        val adapter = EnenDrinkenAdapter(EnenDrinkenClickListener { winkelwagenItemAantal ->
            viewModel.onWinkelwagenItemClicked(winkelwagenItemAantal)
        })


        binding.winkelwagenItemsRecycleView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer {
            binding.Verder.visibility = View.GONE
            binding.statusImage.visibility = View.GONE
            binding.errorMessage.visibility = View.GONE
            if (it.equals(ScoutsArduApiStatus.ERROR)) {
                binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                binding.errorMessage.visibility = View.VISIBLE
                binding.statusImage.visibility = View.VISIBLE
            }
            if (it.equals(ScoutsArduApiStatus.LOADING)) {
                binding.statusImage.setImageResource(R.drawable.loading_animation)
                binding.statusImage.visibility = View.VISIBLE
            }
            if (it.equals(ScoutsArduApiStatus.DONE)) {
                binding.Verder.visibility = View.VISIBLE
            }
        })
        //navigation: altijd in fragment
        binding.Verder.setOnClickListener {
            var verderGaanToegestaan = false
            var wagen = Winkelwagen(0, 0,0,0,0,0, ArrayList(), false, AccountRepository.gebruiker!!)
            viewModel.items.value?.forEach {
                if (it.aantal > 0) {
                    verderGaanToegestaan = true
                    wagen.winkelwagenItems.add(it)
                }
            }
            if (verderGaanToegestaan) {
                val action =
                    EnenDrinkenFragmentDirections.actionEnenDrinkenFragmentToCheckWinkelwagenFragment(
                        wagen
                    )
                Navigation.findNavController(it).navigate(action)
            } else {
                binding.errorMessage.text = "Koop minstens 1 product!"
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        return binding.root
    }


}
