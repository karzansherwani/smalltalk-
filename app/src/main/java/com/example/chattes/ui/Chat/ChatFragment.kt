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
import com.example.chattes.Database.ChatObject
import com.example.chattes.R
import com.example.chattes.hideKeyboard
import com.example.chattes.ui.login.LoginActivity
import java.util.*
import kotlin.concurrent.fixedRateTimer


class ChatFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel

    private lateinit var logOutButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatInput: EditText
    private lateinit var sendButton: Button

    private var timer: Timer? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonListeners()
        initRecyclerView()
    }


    override fun onResume() {
        super.onResume()

        startChatFetchTimer()
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
        timer = null
    }



    private fun setButtonListeners() {

        logOutButton.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences(
                LoginActivity.SHARED_PREF_FILENAME,
                Context.MODE_PRIVATE
            )
            sharedPref?.edit()?.putBoolean(LoginActivity.LOGGED_IN_KEY, false)?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }
        sendButton.setOnClickListener {
            val text = chatInput.text.toString()
            val chatObject = ChatObject("ln2lceAzGo", "karzan", text)

            if (text.isNotEmpty()) {
                chatViewModel.sendChatMessage(
                    Volley.newRequestQueue(context),
                    chatObject
                ) { sucess ->
                    if (sucess) {
                        activity?.hideKeyboard()
                        chatInput.setText("")
                        chatAdapter.addInstance(chatObject)
                        scrollToBottom()
                    } else {
                        Toast.makeText(
                            context, "Noe gikk galt",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
        }
    }

    private fun getChatMessages() {
        chatViewModel.getChatMessages(
            Volley.newRequestQueue(context),

            { chatMessages ->
                chatAdapter.updateData(chatMessages)
                scrollToBottom()
            },
            {
                Toast.makeText(
                    context,
                    "Noe gikk galt. Kunne ikke hente meldinger.",
                    Toast.LENGTH_LONG
                ).show()
            })
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition((recyclerView.adapter?.itemCount ?: 1) - 1)
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        chatAdapter = ChatAdapter(
            listOf()
        )
        recyclerView.adapter = chatAdapter
    }

    private fun startChatFetchTimer(){
        if (timer != null) {
            return
        }
        timer = fixedRateTimer("chatFetchTimer",daemon = false, 0L, 5 * 1000){
            getChatMessages()
        }
    }


}