package work.risingcamp.week6_7_game

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.json.JSONObject
import work.risingcamp.week6_7_game.databinding.ActivityLastBinding

class LastActivity : AppCompatActivity() {
    private val mapKey = "map"
    private lateinit var etName : EditText
    private lateinit var binding : ActivityLastBinding
    private var hash : HashMap<String,Int> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        var hash = HashMap<String, Int>()
        hash["랜덤값1"] = 0
        hash["랜덤값2"] = 0
        hash["랜덤값3"] = 0

        val json = JSONObject(hash as Map<*,*>)

        val shared = applicationContext.getSharedPreferences(
            "RANK", Context.MODE_PRIVATE
        ) ?: return
        with(shared.edit()){
            putString("mapKey",json.toString())
            commit()
        }
        binding = ActivityLastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGamestart.setOnClickListener {
            var intent = Intent(this,GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
        }
        binding.btnSaveMyrecord.setOnClickListener {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.ranking_dialog, null)

            val alertDialog = AlertDialog.Builder(this)
                .setPositiveButton("저장") { _, _ ->
                    etName = view.findViewById(R.id.et_rank_name)
                    saveLocal(view)
                }
                .setNeutralButton("취소",null)
                .create()
            alertDialog.setCancelable(false)
            alertDialog.setView(view)
            alertDialog.show()
        }

    }

    override fun onStart() {
        super.onStart()
        val outputMap: MutableMap<String, Int> = loadMap().toMutableMap()

        if(outputMap != null){
            val keys = outputMap.keys.toList()
            val values = outputMap.values.toList()
            if(values[0] != 0) {
                binding.firstName.text = keys[0]
                binding.tvFirstscore.text = values[0].toString()
                binding.firstName.visibility = View.VISIBLE
                binding.tvFirstscore.visibility = View.VISIBLE
            }
            if(values[1] != 0) {
                binding.secondName.text = keys[1]
                binding.tvSecondscore.text = values[1].toString()
                binding.secondName.visibility = View.VISIBLE
                binding.tvSecondscore.visibility = View.VISIBLE
            }
            if(values[2] != 0) {
                binding.thirdName.text = keys[2]
                binding.tvThirdscore.text = values[2].toString()
                binding.thirdName.visibility = View.VISIBLE
                binding.tvThirdscore.visibility = View.VISIBLE
            }
        }
    }
    private fun loadMap(): Map<String, Int> {
        var outputMap: MutableMap<String, Int> = HashMap()
        val pSharedPref = applicationContext.getSharedPreferences(
            "RANK", Context.MODE_PRIVATE
        )
        if (pSharedPref != null) {
            val jsonString = pSharedPref.getString(mapKey, JSONObject().toString())
            val jsonObject = JSONObject(jsonString)
            val keysItr = jsonObject.keys()
            while (keysItr.hasNext()) {
                val key = keysItr.next()
                outputMap[key]
            }
        }
        if(outputMap != null){
            Log.d("print",outputMap.values.toString())
            outputMap = outputMap.toList().sortedByDescending { it.second }.toMap() as MutableMap
        }
        return outputMap
    }
    private fun saveLocal(view: View) {
        val name = etName.text.toString().trim()
        when {
            else -> {
                if(intent.getIntExtra("score",0) != null) {
//                    inputMap = loadMap() as MutableMap<String, Int>
                    hash[name] = intent.getIntExtra("score",0)
                    Log.d("name",name)
                    Log.d("score",intent.getIntExtra("score",0)!!.toString())
                }
                saveMap(hash)
                Toast.makeText(applicationContext, "Saved Locally!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveMap(inputMap: MutableMap<String, Int>) {
        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences(
            "RANK", Context.MODE_PRIVATE
        )
        val jsonObject = JSONObject(inputMap as Map<*, *>?)
        val jsonString = jsonObject.toString()
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(mapKey).apply()
        editor.putString(mapKey, jsonString)
        editor.commit()
    }
}
