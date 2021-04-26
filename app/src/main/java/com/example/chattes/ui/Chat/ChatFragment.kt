package com.example.chattes.ui.Chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.example.chattes.R
import com.example.chattes.ui.login.LoginActivity
import kotlinx.android.synthetic.main.chat_fragment.*
import kotlin.concurrent.fixedRateTimer

class ChatFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel

    private lateinit var logOutButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatInput: EditText
    private lateinit var sendButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatViewModel =
            ViewModelProvider(this).get(ChatViewModel::class.java)
        val view = inflater.inflate(R.layout.chat_fragment, container, false)

        chatViewModel = ViewModelProvider(this).get(chatViewModel::class.java)

        logOutButton = view.findViewById(R.id.log_out_button)
        recyclerView = view.findViewById(R.id.chat_recycler_view)
        chatInput = view.findViewById(R.id.chat_input)
        sendButton = view.findViewById(R.id.send_message_button)

        return view
    }
}
