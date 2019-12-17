package be.ardu.scoutsardu


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import be.ardu.scoutsardu.adapters.HistoriekAdapter
import be.ardu.scoutsardu.adapters.HistoriekClickListener
import be.ardu.scoutsardu.databinding.FragmentHistoriekBinding
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.repositories.HistoriekDatabaseRepository
import be.ardu.scoutsardu.viewmodels.HistoriekViewModel
import be.ardu.scoutsardu.viewmodels.HistoriekViewModelFactory


/**
 * A simple [Fragment] subclass.
 */
class HistoriekFragment : Fragment() {
    private lateinit var viewModel: HistoriekViewModel
    private lateinit var viewModelFactory: HistoriekViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHistoriekBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_historiek,
                container,
                false
            )

        viewModelFactory = HistoriekViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoriekViewModel::class.java)
        binding.historiekViewModel = viewModel
        binding.lifecycleOwner = this

        val displayMetrics = context!!.resources.displayMetrics
        binding.mijnHistoriek.width = displayMetrics.widthPixels / 2
        binding.stamHistoriek.width = displayMetrics.widthPixels / 2

        binding.mijnHistoriek.setBackgroundColor(
            ContextCompat.getColor(context!!, R.color.colorPrimary)
        )
        binding.stamHistoriek.setBackgroundColor(
            ContextCompat.getColor(
                context!!,
                R.color.colorPrimaryLight
            )
        )
        binding.mijnHistoriek.setOnClickListener {
            viewModel.getMijnHistoriek()
            it.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            binding.stamHistoriek.setBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.colorPrimaryLight
                )
            )
        }

        binding.stamHistoriek.setOnClickListener {
            viewModel.getStamHistoriek()
            it.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            binding.mijnHistoriek.setBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.colorPrimaryLight
                )
            )
        }


        viewModel.status.observe(viewLifecycleOwner, Observer {
            binding.statusImage.visibility = View.GONE
            binding.errorMessage.visibility = View.GONE
            if (it == ScoutsArduApiStatus.ERROR) {
                binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                binding.errorMessage.visibility = View.VISIBLE
                binding.winkelwagens.visibility = View.VISIBLE
            }
            if (it == ScoutsArduApiStatus.LOADING) {
                binding.statusImage.setImageResource(R.drawable.loading_animation)
                binding.statusImage.visibility = View.VISIBLE
                binding.winkelwagens.visibility = View.GONE
            }
            if (it == ScoutsArduApiStatus.DONE) {
                binding.winkelwagens.visibility = View.VISIBLE
            }
        })

        viewModel.navigateToSanteFragemt.observe(this, Observer { winkelwagen ->
            winkelwagen?.let {
                this.findNavController().navigate(
                    HistoriekFragmentDirections.actionHistoriekFragmentToSanteFragment(
                        it
                    )
                )
                viewModel.onWinkelwagenToSanteFragmentNavigated()
            }
        })

        val adapter =
            HistoriekAdapter(HistoriekClickListener { winkelwagen ->
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


}
