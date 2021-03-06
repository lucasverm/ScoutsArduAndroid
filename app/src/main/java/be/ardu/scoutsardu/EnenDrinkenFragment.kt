package be.ardu.scoutsardu


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import be.ardu.scoutsardu.adapters.EnenDrinkenAdapter
import be.ardu.scoutsardu.adapters.EnenDrinkenClickListener
import be.ardu.scoutsardu.databinding.FragmentEnenDrinkenBinding
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.viewmodels.EnenDrinkenViewModel
import be.ardu.scoutsardu.viewmodels.EnenDrinkenViewModelFactory


/**
 * A simple [Fragment] subclass.
 */
class EnenDrinkenFragment : Fragment() {

    private lateinit var viewModel: EnenDrinkenViewModel
    private lateinit var viewModelFactory: EnenDrinkenViewModelFactory

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentEnenDrinkenBinding =
            DataBindingUtil.inflate(
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

        binding.lifecycleOwner = this

        val adapter =
            EnenDrinkenAdapter(EnenDrinkenClickListener { winkelwagenItemAantal ->
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
            if (it == ScoutsArduApiStatus.ERROR) {
                binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                binding.errorMessage.visibility = View.VISIBLE
                binding.statusImage.visibility = View.VISIBLE
            }
            if (it == ScoutsArduApiStatus.LOADING) {
                binding.statusImage.setImageResource(R.drawable.loading_animation)
                binding.statusImage.visibility = View.VISIBLE
            }
            if (it == ScoutsArduApiStatus.DONE) {
                binding.Verder.visibility = View.VISIBLE
            }
        })
        //navigation: altijd in fragment
        binding.Verder.setOnClickListener { it ->
            var verderGaanToegestaan = false
            val wagen =
                Winkelwagen(
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    ArrayList(),
                    false,
                    viewModel.getGebruiker()
                )
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
