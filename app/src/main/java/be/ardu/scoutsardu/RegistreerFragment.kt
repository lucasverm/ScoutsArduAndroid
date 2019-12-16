package be.ardu.scoutsardu


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import be.ardu.scoutsardu.databinding.FragmentRegistreerBinding
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.viewmodels.RegistreerViewModel
import be.ardu.scoutsardu.viewmodels.RegistreerViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class RegistreerFragment : Fragment() {
    private lateinit var viewModel: RegistreerViewModel
    private lateinit var viewModelFactory: RegistreerViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentRegistreerBinding>(
            inflater,
            R.layout.fragment_registreer,
            container,
            false
        )
        binding.registreerButton.setOnClickListener {
            val action =
                RegistreerFragmentDirections.actionRegistreerFragmentToLoginFragment()
            Navigation.findNavController(view!!).navigate(action)
        }

        binding.alEenAccountButton.setOnClickListener {
            val action =
                RegistreerFragmentDirections.actionRegistreerFragmentToLoginFragment()
            Navigation.findNavController(view!!).navigate(action)
        }

        viewModelFactory = RegistreerViewModelFactory()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RegistreerViewModel::class.java)
        binding.registreerViewModel = viewModel

        binding.registreerButton.setOnClickListener {
            when {
                binding.email.text.toString().isBlank() -> {
                    binding.errorMessage.text = "Vul je email adress in!"
                    binding.errorMessage.isVisible = true
                    binding.errorMessage.setBackgroundColor(Color.RED)
                }
                binding.voornaamInput.text.toString().isBlank() -> {
                    binding.errorMessage.text = "Vul je voornaam in!"
                    binding.errorMessage.isVisible = true
                    binding.errorMessage.setBackgroundColor(Color.RED)
                }
                binding.achternaamInput.text.toString().isBlank() -> {
                    binding.errorMessage.text = "Vul je achternaam in!"
                    binding.errorMessage.isVisible = true
                    binding.errorMessage.setBackgroundColor(Color.RED)
                }
                binding.telefoonNummerInput.text.toString().isBlank() -> {
                    binding.errorMessage.text = "Vul je telefoon nummer in!"
                    binding.errorMessage.isVisible = true
                    binding.errorMessage.setBackgroundColor(Color.RED)
                }
                binding.password.text.toString().isBlank() -> {
                    binding.errorMessage.text = "Vul je wachtwoord in!"
                    binding.errorMessage.isVisible = true
                    binding.errorMessage.setBackgroundColor(Color.RED)
                }
                binding.passwordBevestiging.text.toString().isBlank() -> {
                    binding.errorMessage.text = "Vul de wachtwoord bevestiging in!"
                    binding.errorMessage.isVisible = true
                    binding.errorMessage.setBackgroundColor(Color.RED)
                }
                binding.password.text.toString() != binding.passwordBevestiging.text.toString() -> {
                    binding.errorMessage.text = "De wachtwoorden komen niet overeen!"
                    binding.errorMessage.isVisible = true
                    binding.errorMessage.setBackgroundColor(Color.RED)
                }
                else -> viewModel.registreer(
                    binding.email.text.toString(),
                    binding.voornaamInput.text.toString(),
                    binding.achternaamInput.text.toString(),
                    binding.telefoonNummerInput.text.toString(),
                    binding.password.text.toString(),
                    binding.passwordBevestiging.text.toString()
                )
            }

        }

        binding.errorMessage.visibility = View.GONE
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it == ScoutsArduApiStatus.DONE) {
                binding.errorMessage.text = "Welkom!"
                binding.errorMessage.setBackgroundColor(Color.GREEN)
                binding.errorMessage.visibility = View.VISIBLE
                val intent: Intent = Intent(this.context, MainActivity::class.java)
                startActivity(intent)
                this.activity!!.finish()
            }
            if (it == ScoutsArduApiStatus.LOADING) {
                binding.errorMessage.text = "Verbinden met server..."
                binding.errorMessage.setBackgroundColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.orange
                    )
                )
                binding.errorMessage.visibility = View.VISIBLE
            }

            if (it == ScoutsArduApiStatus.ERROR) {
                binding.errorMessage.setBackgroundColor(Color.RED)
                binding.errorMessage.text = "Er liep iets fout: controleer je gegevens!"
                binding.errorMessage.visibility = View.VISIBLE
            }

        })
        return binding.root
    }

}
