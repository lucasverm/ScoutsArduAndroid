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
import androidx.navigation.fragment.findNavController
import be.ardu.scoutsardu.adapters.PortefeuilleAdapter
import be.ardu.scoutsardu.adapters.PortefeuilleClickListener
import be.ardu.scoutsardu.databinding.FragmentPortefeuilleBinding
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.viewmodels.PortefeuilleViewModel
import be.ardu.scoutsardu.viewmodels.PortefeuilleViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class PortefeuilleFragment : Fragment() {
    private lateinit var viewModel: PortefeuilleViewModel
    private lateinit var viewModelFactory: PortefeuilleViewModelFactory
    private lateinit var binding: FragmentPortefeuilleBinding
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_portefeuille,
                container,
                false
            )
        viewModelFactory = PortefeuilleViewModelFactory()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PortefeuilleViewModel::class.java)
        binding.portefeuilleViewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.status.observe(viewLifecycleOwner, Observer {
            binding.statusImage.visibility = View.GONE
            binding.errorMessage.visibility = View.GONE
            if (it == ScoutsArduApiStatus.ERROR) {
                setSchulden()
                binding.statusImage.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
            if (it == ScoutsArduApiStatus.LOADING) {
                binding.statusImage.setImageResource(R.drawable.loading_animation)
                binding.statusImage.visibility = View.VISIBLE
            }
            if (it == ScoutsArduApiStatus.DONE) {
                setSchulden()
            }
        })

        viewModel.navigateToSanteFragemt.observe(this, Observer {winkelwagen ->
            winkelwagen?.let {
                this.findNavController().navigate(
                    PortefeuilleFragmentDirections.actionPortefeuilleFragmentToSanteFragment(
                        it
                    )
                )
                viewModel.onWinkelwagenToSanteFragmentNavigated()
            }
        })

        val adapter =
            PortefeuilleAdapter(PortefeuilleClickListener { winkelwagen ->
                viewModel.onWinkelwagenItemClicked(winkelwagen)
            })
        binding.winkelwagens.adapter = adapter

        viewModel.winkelwagens.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        return binding.root
    }

    private fun setSchulden(){
        binding.schulden.text = "Uw totale schuld: € " + viewModel.berekenTotaleSchuld().toString()
    }


}
