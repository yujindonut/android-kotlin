package work.risingcamp.week02_kakaotalk_____

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import work.risingcamp.week02_kakaotalk_____.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onStart() {
        super.onStart()
        binding.btSumbit.setOnClickListener{
            binding.tvChatting.text = binding.etChat.text
            binding.tvChatting.visibility = View.VISIBLE
        }

    }

    override fun onPause() {
        super.onPause()
        binding.tvChatting.visibility = View.INVISIBLE
        binding.tvChatting.text = ""
    }
}

