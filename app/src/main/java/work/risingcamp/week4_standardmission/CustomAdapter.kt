package work.risingcamp.week4_standardmission

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import work.risingcamp.week4_standardmission.databinding.ViewItemBinding
import java.time.LocalDate

class CustomAdapter(context:Context, private val memoArrayList: ArrayList<Memo>) : BaseAdapter() {
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var binding: ViewItemBinding

    override fun getCount(): Int = memoArrayList.size

    override fun getItem(position: Int): Any = memoArrayList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = ViewItemBinding.inflate(inflater, parent,false)
        binding.tvTitle.text = memoArrayList[position].title
        binding.tvContent.text = memoArrayList[position].content
        binding.tvDate.text = LocalDate.now().toString()

        binding.memoSwitch.setChecked(memoArrayList[position].isSwitched)
        binding.memoSwitch.setOnCheckedChangeListener { _, onSwitch -> memoArrayList[position].isSwitched = onSwitch }
        return binding.root
    }
}