package be.ardu.scoutsardu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import be.ardu.scoutsardu.databinding.FragmentDashboardBinding

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentDashboardBinding>(inflater, R.layout.fragment_dashboard, container, false)
        binding.enenDrinken.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_enenDrinkenFragment))
        binding.historiek.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_historiekFragment))
        binding.portefeuille.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_portefeuilleFragment))
        binding.account.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_accountFragment))
        return binding.root
    }


}
