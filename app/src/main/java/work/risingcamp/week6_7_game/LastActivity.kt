package work.risingcamp.week6_7_game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import work.risingcamp.week6_7_game.databinding.ActivityLastBinding

class LastActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = intent

        val sharedPreferences = getSharedPreferences("GiveUp", MODE_PRIVATE)
        if(sharedPreferences != null){
            val score = sharedPreferences.getString("score","0")!!
            binding.lastActivityScore.text = score + "점"
            binding.lastActivityScore.visibility = View.VISIBLE
        }
        val sharedPreferences2 = getSharedPreferences("TimeOut", MODE_PRIVATE)
        if(sharedPreferences2 != null){
            val score = sharedPreferences2.getString("score","0")!!
            binding.lastActivityScore.text = score + "점"
            binding.lastActivityScore.visibility = View.VISIBLE
        }
        binding.tvResult.text = intent.getStringExtra("result").toString()

        binding.btnGamestart.setOnClickListener {
            var intent = Intent(this,GameActivity::class.java)
            startActivity(intent)
        }
    }
}