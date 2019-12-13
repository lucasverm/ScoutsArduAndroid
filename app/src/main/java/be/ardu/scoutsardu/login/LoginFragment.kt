package be.ardu.scoutsardu


import android.graphics.Color
import android.os.Bundle
import android.view.InflateException
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import be.ardu.scoutsardu.checkWinkelwagen.CheckWinkelwagenViewModel
import be.ardu.scoutsardu.checkWinkelwagen.CheckWinkelwagenViewModelFactory
import be.ardu.scoutsardu.databinding.FragmentLoginBinding
import be.ardu.scoutsardu.login.LoginViewModel
import be.ardu.scoutsardu.login.LoginViewModelFactory
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.sante.SanteFragmentDirections

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)
        //binding.loginButton.setOnClickListener (
           // Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_dashboardFragment))

        viewModelFactory = LoginViewModelFactory()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel

        binding.loginButton.setOnClickListener{
            //viewModel.login(binding.email.text.toString(), binding.password.text.toString())
            viewModel.login("user@example.com", "string")
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it == ScoutsArduApiStatus.DONE){
                binding.errorMessage.visibility = View.GONE
                var action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
                Navigation.findNavController(view!!).navigate(action)
            }
            if(it == ScoutsArduApiStatus.LOADING){
                binding.errorMessage.text ="Verbinding met server..."
                binding.errorMessage.setBackgroundColor(Color.CYAN)
                binding.errorMessage.visibility = View.VISIBLE
            }

            if(it == ScoutsArduApiStatus.ERROR){
                binding.errorMessage.text ="Er liep iets fout!"
                binding.errorMessage.visibility = View.VISIBLE
            }

        })

        return binding.root
    }



}