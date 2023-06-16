package com.bangkit.rahayoo.ui.chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.rahayoo.R
import com.bangkit.rahayoo.data.model.Chat
import com.bangkit.rahayoo.databinding.FragmentChatBinding
import com.bangkit.rahayoo.di.Injection
import com.bangkit.rahayoo.ui.ViewModelFactory


class ChatFragment : Fragment(), View.OnClickListener {
    
    companion object {
        private const val TAG = "ChatFragment"
    }

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels {
        ViewModelFactory(Injection.provideRepository(requireContext()))
    }

    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSend.setOnClickListener(this)
        binding.btnChatSample1.setOnClickListener(this)
        binding.btnChatSample2.setOnClickListener(this)

        viewModel.chatLog.observe(viewLifecycleOwner) {
            val chatAdapter = ChatAdapter()
            binding.rvChat.adapter = chatAdapter
            chatAdapter.submitList(it)
            Log.d(TAG, "onViewCreated: $it")
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.lpiChat.visibility = View.VISIBLE
            } else binding.lpiChat.visibility = View.GONE
        }
    }

    private fun closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        val view: View? = requireActivity().currentFocus

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            val manager: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            manager.hideSoftInputFromWindow(
                view.windowToken, 0
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_send -> {
                closeKeyboard()
                sendMessage()
            }

            R.id.btn_chat_sample_1 -> {
                binding.etChat.setText(getString(R.string.chat_sample_1))
                sendMessage()
            }

            R.id.btn_chat_sample_2 -> {
                binding.etChat.setText(getString(R.string.chat_sample_2))
                sendMessage()
            }
        }
    }

    private fun sendMessage() {
        val message = binding.etChat.text.toString()
        if (message.isEmpty()) return
        binding.etChat.setText("")
        binding.etChat.clearFocus()
        val chat = Chat(
            "chat_user_$counter",
            "user",
            message
        )

        viewModel.sendMessage(chat)

        if (counter == 0) {
            // Hide the sample chat
            binding.btnChatSample1.visibility = View.GONE
            binding.btnChatSample2.visibility = View.GONE
        }

        counter++
    }
}