package `in`.ac.bits_hyderabad.swd.swd.user.activity

import `in`.ac.bits_hyderabad.swd.swd.R
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        prefs = applicationContext.getSharedPreferences("USER_LOGIN_DETAILS", Context.MODE_PRIVATE)
//        val uid = prefs.getString("uid", null)
//        val pwd = prefs.getString("password", null)
//        val id_no = prefs.getString("id", null)

        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.setupWithNavController(Navigation.findNavController(this, R.id.nav_host_fragment))
    }

    fun showDialog() {
        val dialogBuilder: AlertDialog.Builder
        dialogBuilder = AlertDialog.Builder(this@MainActivity, R.style.AppCompatAlertDialogStyle)
        dialogBuilder.setTitle("New Update Available!!")
        dialogBuilder.setMessage("A new Version of the app is available.")
        dialogBuilder.setPositiveButton("Update") { dialog, which ->
            val appPackageName = "in.ac.bits_hyderabad.swd.swd" // getPackageName() from Context or Activity object
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
            } catch (anfe: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
            }
        }
                .setNegativeButton("Cancel") { dialog, which -> //Do nothing
                }.setOnCancelListener { }
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

}