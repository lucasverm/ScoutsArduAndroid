package be.ardu.scoutsardu.checkWinkelwagen


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.databinding.FragmentCheckWinkelwagenBinding
import be.ardu.scoutsardu.models.Winkelwagen
import androidx.recyclerview.widget.DividerItemDecoration





class CheckWinkelwagenFragment : Fragment() {
    private lateinit var viewModel: CheckWinkelwagenViewModel
    private lateinit var viewModelFactory: CheckWinkelwagenViewModelFactory
    private var data: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCheckWinkelwagenBinding =
            DataBindingUtil.inflate<FragmentCheckWinkelwagenBinding>(
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
        }
        binding.Verder.text =  "Totaal: € " + viewModel.winkelwagen.value!!.getTotaal().toString()
        binding.Verder.setOnClickListener {
            var action = CheckWinkelwagenFragmentDirections.actionCheckWinkelwagenFragmentToSanteFragment2(
                viewModel.winkelwagen.value!!
            )
            Navigation.findNavController(it).navigate(action)
        }

        return binding.root
    }


}
