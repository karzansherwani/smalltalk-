package com.example.chattes.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.toolbox.Volley
import com.example.chattes.R
import com.example.chattes.tabbar.MainActivity
import com.example.chattes.ui.Chat.ChatFragment

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        loginButton = view.findViewById(R.id.login_Button)
        usernameEditText = view.findViewById(R.id.login_username_input)
        passwordEditText = view.findViewById(R.id.login_password_input)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()

    }

    private fun setButtons() {
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.logInUser(
                Volley.newRequestQueue(context),
                username,
                password
            ){sucess ->
                if (sucess){
                    val sharedpref = activity?.getSharedPreferences(
                        LoginActivity.SHARED_PREF_FILENAME,
                    Context.MODE_PRIVATE
                    )
                    sharedpref?.edit()?.putBoolean(LoginActivity.LOGGED_IN_KEY,true)?.apply()

                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "Wrong username or password", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}