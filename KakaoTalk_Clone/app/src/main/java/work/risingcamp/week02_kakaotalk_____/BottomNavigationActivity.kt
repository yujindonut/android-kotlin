package work.risingcamp.week02_kakaotalk_____

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import work.risingcamp.week02_kakaotalk_____.databinding.ActivityBottomNavigationBinding
import work.risingcamp.week02_kakaotalk_____.databinding.ActivityChattingBinding

class BottomNavigationActivity : AppCompatActivity() {
    private val fragmentOne by lazy { FriendFragment() }
    private val fragmentTwo by lazy { ChatFragment() }
    private lateinit var binding : ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationBar()
    }

    private fun initNavigationBar() {
        binding.bnvMain.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_friend -> {
                        changeFragment(fragmentOne)
                    }
                    R.id.navigation_chat -> {
                        changeFragment(fragmentTwo)
                    }

                }
                true
            }
            selectedItemId = R.id.navigation_friend
        }
    }

    fun getName() : String {
        Log.d("name",intent.getStringExtra("username").toString())
        return intent.getStringExtra("username").toString()
    }
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment).commit()
    }


}