package be.ardu.scoutsardu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import be.ardu.scoutsardu.R
import be.ardu.scoutsardu.databinding.ActivityMainBinding
import android.content.Intent




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_picto)
        ///**
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
        val navController = this.findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        //**/
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        moveTaskToBack(true);
    }

}
