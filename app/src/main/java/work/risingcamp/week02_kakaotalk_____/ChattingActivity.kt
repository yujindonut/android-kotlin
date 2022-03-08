package work.risingcamp.week02_kakaotalk_____

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import work.risingcamp.week02_kakaotalk_____.databinding.ActivityChattingBinding
import work.risingcamp.week02_kakaotalk_____.databinding.ActivityFriendBinding

class ChattingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChattingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        var chat = binding.etChat.text.toString()
        binding.btSumbit.setOnClickListener {
            binding.tvChatting.text = chat
            binding.tvChatting.visibility = View.VISIBLE
        }

    }
}