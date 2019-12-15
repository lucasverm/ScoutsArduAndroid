package be.ardu.scoutsardu.historiek


import android.graphics.Color
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
import androidx.navigation.fragment.findNavController
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.databinding.FragmentHistoriekBinding
import be.ardu.scoutsardu.network.ScoutsArduApiStatus


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
        // Inflate the layout for this fragment
        val binding: FragmentHistoriekBinding =
            DataBindingUtil.inflate<FragmentHistoriekBinding>(
                inflater,
                R.layout.fragment_historiek,
                container,
                false
            )

        viewModelFactory = HistoriekViewModelFactory()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(HistoriekViewModel::class.java)
        binding.historiekViewModel = viewModel

        binding.setLifecycleOwner(this)

        val displayMetrics = context!!.getResources().displayMetrics
        binding.mijnHistoriek.width = displayMetrics.widthPixels/2
        binding.stamHistoriek.width = displayMetrics.widthPixels/2

        binding.mijnHistoriek.setBackgroundColor(
            ContextCompat.getColor(context!!, R.color.colorPrimary))
        binding.stamHistoriek.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimaryLight))
        binding.mijnHistoriek.setOnClickListener{
            viewModel.getMijnHistoriek()
            it.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            binding.stamHistoriek.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimaryLight))
        }

        binding.stamHistoriek.setOnClickListener{
            viewModel.getStamHistoriek()
            it.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            binding.mijnHistoriek.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimaryLight))
        }


        viewModel.status.observe(viewLifecycleOwner, Observer {
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

            }
        })


       /* viewModel.winkelwagens.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })*/

        viewModel.navigateToSanteFragemt.observe(this, Observer {winkelwagen ->
            winkelwagen?.let {
                this.findNavController().navigate(
                    HistoriekFragmentDirections.actionHistoriekFragmentToSanteFragment(it)
                )
                viewModel.onWinkelwagenToSanteFragmentNavigated()
            }
        })

        val adapter = HistoriekAdapter(HistoriekClickListener { winkelwagen ->
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
