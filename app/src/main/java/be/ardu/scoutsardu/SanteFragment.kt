package be.ardu.scoutsardu


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import be.ardu.scoutsardu.adapters.SanteAdapter
import be.ardu.scoutsardu.databinding.FragmentSanteBinding
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.viewmodels.SanteViewModel
import be.ardu.scoutsardu.viewmodels.SanteViewModelFactory





/**
 * A simple [Fragment] subclass.
 */
class SanteFragment : Fragment() {
    private lateinit var viewModel: SanteViewModel
    private lateinit var viewModelFactory: SanteViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSanteBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_sante,
                container,
                false
            )

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
            viewModel.winkelwagenIsHistory = it.get("history") as Boolean
        }

        if(viewModel.winkelwagenIsHistory){
            viewModel.postWinkelwagen()
        } else {
            binding.statusImage.visibility = View.GONE
        }

        updateVelden(binding)

        viewModel.status.observe(viewLifecycleOwner, Observer {
           if(it == ScoutsArduApiStatus.LOADING){
               binding.betaaldOverzichtLijst.visibility = View.GONE
               binding.bestelling.visibility = View.GONE
               binding.betaald.visibility = View.GONE
               binding.datum.visibility = View.GONE
               binding.naam.visibility = View.GONE
               binding.tijdstip.visibility = View.GONE
               binding.statusImage.setImageResource(R.drawable.loading_animation)
               binding.statusImage.visibility = View.VISIBLE

           }
            if(it == ScoutsArduApiStatus.DONE){
                updateVelden(binding)
                binding.betaaldOverzichtLijst.visibility = View.VISIBLE
                binding.bestelling.visibility = View.VISIBLE
                binding.betaald.visibility = View.VISIBLE
                binding.naam.visibility = View.VISIBLE
                binding.datum.visibility = View.VISIBLE
                binding.tijdstip.visibility = View.VISIBLE
                binding.statusImage.visibility = View.GONE
            }
            if(it == ScoutsArduApiStatus.ERROR){
                val action =
                    SanteFragmentDirections.actionSanteFragmentToCheckWinkelwagenFragment(
                        viewModel.winkelwagen.value!!
                    )
                Navigation.findNavController(view!!).navigate(action)
            }
        })

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateVelden(binding: FragmentSanteBinding){
        binding.naam.text = "Naam: " + viewModel.winkelwagen.value?.gebruiker!!.getFullNaam()
        binding.betaald.text = if(viewModel.winkelwagen.value!!.betaald) "Betaald: Ja!" else "Betaald: Neen!"
        binding.datum.text = "Datum: " + viewModel.winkelwagen.value!!.getDatum()
        binding.tijdstip.text = "Tijdstip: " + viewModel.winkelwagen.value!!.getTijd()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This callback will only be called when MyFragment is at least Started.
        super.onCreate(savedInstanceState)
        // This callback will only be called when MyFragment is at least Started.
        val callback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                println("-------")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        // The callback can be enabled or disabled here or in the lambda
    }


}
