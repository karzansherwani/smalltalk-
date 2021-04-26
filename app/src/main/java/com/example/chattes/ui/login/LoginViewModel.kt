package com.example.chattes.ui.login

import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.example.chattes.Database.UserObject
import com.google.gson.Gson
import java.lang.Exception

class LoginViewModel : ViewModel() {
    fun logInUser(requestQueue: RequestQueue, username: String,password: String,callBack: (Boolean) -> Unit){
    var url =  "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/login"
        url +="?username=$username&password=$password"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {jsonResponse ->
                try {
                    val user = Gson().fromJson(jsonResponse, UserObject::class.java)
                    callBack(true)
                } catch (e: Exception){
               callBack(false) }
            },
            {error ->
                callBack(false)
            }
        )
        requestQueue.add(stringRequest)
    }
}
