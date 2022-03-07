package work.risingcamp.week4_standardmission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import work.risingcamp.week4_standardmission.databinding.ActivityAddBinding

class AddActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etTitle.setSelection(0)
        binding.etContent.setSelection(0)
//        커서를 위쪽에 주기 위함

        binding.floatingActionButton.setOnClickListener {

        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.tvAdd.setOnClickListener {
            if (intent.getIntExtra("index",100) == 100)
            {
                intent.putExtra("title", binding.etTitle.text.toString())
                intent.putExtra("content", binding.etContent.text.toString())
                setResult(100, intent)
            }else if (intent.getIntExtra("index",100) == 200){
                intent.putExtra("title", binding.etTitle.text.toString())
                intent.putExtra("content", binding.etContent.text.toString())
                intent.putExtra("position",intent.getIntExtra("position",0))
                setResult(200, intent)
            }
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        if(intent.getIntExtra("index",100) == 200){
            binding.etTitle.setText(intent.getStringExtra("title").toString())
            binding.etContent.setText(intent.getStringExtra("content").toString())
        }
    }
}


