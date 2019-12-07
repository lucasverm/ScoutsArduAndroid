package be.ardu.scoutsardu.enen_drinken


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.databinding.FragmentEnenDrinkenBinding
import be.ardu.scoutsardu.models.Winkelwagen

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
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (viewModel.items.value?.size) {
                null -> 3
                0 -> 3
                else -> 1
            }
        }
        binding.winkelwagenItemsRecycleView.layoutManager = manager

        binding.setLifecycleOwner(this)

        /*viewModel.navigateToCheckFragemt.observe(this, Observer {winkelwagenItem ->
            winkelwagenItem?.let {
                println("))))))))))))))))))))")
                this.findNavController().navigate(
                    EnenDrinkenFragmentDirections.actionEnenDrinkenFragmentToCheckWinkelwagenFragment(/*hier parameters meegeven naar vlgnd scherm*/)
                )
                viewModel.onWinkelwagenToCheckFragmentNavigated()
            }
        })*/

        val adapter = EnenDrinkenAdapter(EnenDrinkenClickListener { winkelwagenItem ->
            viewModel.onWinkelwagenItemClicked(winkelwagenItem)
        })


        binding.winkelwagenItemsRecycleView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        //navigation: altijd in fragment
        binding.Verder.setOnClickListener {
            var wagen = Winkelwagen()
            viewModel.items.value?.forEach {
                if (it.Aantal > 0) {
                    wagen.winkelwagenItems.add(it)
                }

            }
            val action =
                EnenDrinkenFragmentDirections.actionEnenDrinkenFragmentToCheckWinkelwagenFragment(
                    wagen
                )
            Navigation.findNavController(it).navigate(action)
        }




        return binding.root
    }


}
