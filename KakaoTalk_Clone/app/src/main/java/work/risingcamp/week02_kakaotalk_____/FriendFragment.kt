package work.risingcamp.week02_kakaotalk_____

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import work.risingcamp.week02_kakaotalk_____.databinding.FragmentFriendBinding

class FriendFragment : Fragment() {

    private lateinit var name: String
    private var _binding: FragmentFriendBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onAttach(context: Context) {
        super.onAttach(context)
        name = (context as BottomNavigationActivity).getName()
//        Log.d("Fragment", name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d("Fragment", name)
//        val tv_name = view?.findViewById<TextView>(R.id.tv_name)
//        if (tv_name != null) {
//            tv_name.text = name
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        binding.tvUsername.text = name
    }
}