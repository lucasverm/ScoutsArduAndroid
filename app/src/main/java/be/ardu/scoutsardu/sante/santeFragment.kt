package be.ardu.scoutsardu.sante


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.checkWinkelwagen.*
import be.ardu.scoutsardu.databinding.FragmentSanteBinding
import be.ardu.scoutsardu.models.Winkelwagen

/**
 * A simple [Fragment] subclass.
 */
class santeFragment : Fragment() {
    private lateinit var viewModel: SanteViewModel
    private lateinit var viewModelFactory: SanteViewModelFactory
    private var data: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentSanteBinding =
            DataBindingUtil.inflate<FragmentSanteBinding>(
                inflater,
                R.layout.fragment_sante,
                container,
                false
            )


        /*-------------------*/
        val adapter = SanteAdapter()
        binding.betaaldOverzichtLijst.adapter = adapter
                viewModelFactory = SanteViewModelFactory()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SanteViewModel::class.java)
        binding.santeViewModel = viewModel

        viewModel.winkelwagen.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it.winkelwagenItems.toList()
            }
        })

        arguments?.let {
            viewModel.winkelwagen.value = it.get("winkelwagen") as Winkelwagen
        }

        /*----------------*/




        return binding.root
    }


}
