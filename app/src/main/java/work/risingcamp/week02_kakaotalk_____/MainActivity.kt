package work.risingcamp.week02_kakaotalk_____

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import work.risingcamp.week02_kakaotalk_____.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var editor : SharedPreferences.Editor
    private lateinit var autoLogin : CheckBox
    private lateinit var username : EditText
    private lateinit var password: EditText
    var check : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun check_autoLogin(){
        if(check){
            startActivity(Intent(this, BottomNavigationActivity::class.java))
        }
    }
    fun saveLogin(){
        val userid = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        Log.d("Main", userid)

        val sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("userid", userid)
        editor.putString("password", password)
        editor.apply()
    }
    override fun onRestart() {
        super.onRestart()
        check_autoLogin()
    }

    override fun onStart() {
        super.onStart()
        val btn_login = binding.btLogin

        btn_login.setOnClickListener {
            var intent = Intent(this, BottomNavigationActivity::class.java)
            intent.putExtra("username",binding.etEmail.text.toString() )
            startActivity(intent)
        }
        binding.cbAutoLogin.setOnClickListener { check =true }
    }
    override fun onPause() {
        super.onPause()
        if(check){
            saveLogin()
        }

    }
    override fun onStop() {
        super.onStop()
        if(!check){
            val sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        }
    }
}