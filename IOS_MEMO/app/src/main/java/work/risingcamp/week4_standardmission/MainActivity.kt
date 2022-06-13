package work.risingcamp.week4_standardmission

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import work.risingcamp.week4_standardmission.databinding.ActivityMainBinding
import work.risingcamp.week4_standardmission.databinding.AlertPopupBinding
import java.time.LocalDate

data class Memo(var title: String, var content : String, val date: LocalDate, var isSwitched : Boolean = false)
//data class SwitchStatus(val position: Int, var switchedCheck :Boolean)
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var customAdapter : CustomAdapter
    private lateinit var alertBinding : AlertPopupBinding
    private val memoArrayList = ArrayList<Memo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        alertBinding = AlertPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (x in 0..2){
            memoArrayList.add(Memo("오늘의 일기1", "밥을 먹었다", LocalDate.now()))
            memoArrayList.add(Memo("오늘의 일기2", "빵을 먹었다", LocalDate.now()))
            memoArrayList.add(Memo("오늘의 일기3", "밥을 두번 먹었다", LocalDate.now()))
            memoArrayList.add(Memo("오늘의 일기4", "빵을 두번 먹었다", LocalDate.now()))
        }
        customAdapter = CustomAdapter(this, memoArrayList)
        binding.listView.adapter = customAdapter

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 100) {
                val title = result.data?.getStringExtra("title") ?: ""
                val content = result.data?.getStringExtra("content") ?: ""
                memoArrayList.add(Memo(title,content,LocalDate.now()))
                Log.d("MainActivity", title)
                customAdapter.notifyDataSetChanged()
            }
        }
        binding.ivAdd.setOnClickListener {
           var intent = Intent(this, AddActivity::class.java)
            intent.putExtra("index", 100)
            resultLauncher.launch(intent)
        }
        binding.listView.setOnItemLongClickListener { parent, view, position, id ->
            memoArrayList.removeAt(position)
            binding.tvCount.text = customAdapter.count.toString() + "개의 메모"
            customAdapter.notifyDataSetChanged()
            true
        }
        val updateLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 200) {
                val title = result.data?.getStringExtra("title") ?: ""
                val content = result.data?.getStringExtra("content") ?: ""
                val position = result.data?.getIntExtra("position",0)
                Log.d("main",title)
                memoArrayList[position!!].title = title
                memoArrayList[position].content = content
                Log.d("MainActivity", title)
                customAdapter.notifyDataSetChanged()
            }
        }
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, AddActivity::class.java)
            intent.putExtra("index", 200)
            intent.putExtra("title", memoArrayList[position].title)
            intent.putExtra("content",memoArrayList[position].content)
            intent.putExtra("position",position)
            updateLauncher.launch(intent)
        }
        binding.tvCount.text = customAdapter.count.toString() + "개의 메모"

        binding.ivDeleteAll.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("경고")
                .create()
            alertBinding.btnOk.setOnClickListener {
                memoArrayList.clear()
                binding.tvCount.text = customAdapter.count.toString() + "개의 메모"
                customAdapter.notifyDataSetChanged()
                alertDialog.dismiss()
            }
            alertBinding.btnCancel.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.setView(alertBinding.root)
            alertDialog.show()
        }

    }
    override fun onResume() {
        super.onResume()
        binding.tvCount.text = customAdapter.count.toString() + "개의 메모"
    }
}