package work.risingcamp.week02_kakaotalk_____

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import work.risingcamp.week02_kakaotalk_____.databinding.ActivityFriendBinding

class FriendActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvUsername.text = intent.getStringExtra("username")
    }
}