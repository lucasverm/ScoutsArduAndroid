package be.ardu.scoutsardu


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import be.ardu.scoutsardu.databinding.FragmentAccountBinding
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.viewmodels.AccountViewModel
import be.ardu.scoutsardu.viewmodels.AccountViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {

    private lateinit var viewModel: AccountViewModel
    private lateinit var viewModelFactory: AccountViewModelFactory
    private lateinit var binding: FragmentAccountBinding
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_account,
                container,
                false
            )
        viewModelFactory = AccountViewModelFactory()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel::class.java)
        binding.accountViewModel = viewModel

       updateGebruiker()

        binding.uitloggen.setOnClickListener{
            val intent = Intent(this.context, LoginActivity::class.java)
            startActivity(intent)
            this.activity!!.finish()
            viewModel.logout()
        }

        binding.wijzigingenOpslaan.setOnClickListener{
            viewModel.wijzigingenOpslaan(binding.voornaamInput.text.toString(), binding.achternaamInput.text.toString(), binding.telefoonNummerInput.text.toString())
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it == ScoutsArduApiStatus.DONE){
                binding.errorMessage.text = "Wijzigingen opgeslaan!"
                binding.errorMessage.setBackgroundColor(Color.GREEN)
                binding.errorMessage.visibility = View.VISIBLE
                updateGebruiker()
            }
            if(it == ScoutsArduApiStatus.LOADING){
                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.text = "Wijzigingen opslaan..."
                binding.errorMessage.setBackgroundColor(ContextCompat.getColor(context!!, R.color.orange))
            }

            if(it == ScoutsArduApiStatus.ERROR){
                binding.errorMessage.text = "Er liep iets fout!"
                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.setBackgroundColor(Color.RED)
            }

        })

        return binding.root
    }


    private fun updateGebruiker(){
        binding.achternaamInput.setText(this.viewModel.getGebruiker().achternaam)
        binding.voornaamInput.setText(this.viewModel.getGebruiker().voornaam)
        binding.telefoonNummerInput.setText(this.viewModel.getGebruiker().telefoonNummer)
    }

}
