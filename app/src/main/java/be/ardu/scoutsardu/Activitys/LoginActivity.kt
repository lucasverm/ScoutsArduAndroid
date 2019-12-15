package be.ardu.scoutsardu.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.ardu.scoutsardu.R
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import be.ardu.scoutsardu.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        /*super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)*/

        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this,
            R.layout.activity_login
        )
        val navController = this.findNavController(R.id.navHostFragmentLogin)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragmentLogin)
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}
