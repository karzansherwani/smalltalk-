package com.example.chattes.ui.login

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.example.chattes.R

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

        requireActivity().getSharedPreferences("", Context.MODE_PRIVATE)

        return view
    }

}