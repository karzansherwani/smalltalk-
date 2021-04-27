package com.example.chattes.ui.Chat


import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.chattes.Database.ChatObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type

class ChatViewModel : ViewModel() {

    fun getChatMessages (
        queue: RequestQueue,
        succesCallback: (List<ChatObject>) -> Unit,
        errorCallback: () -> Unit,
    ){
        val url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/messages"

        val stringRequest = StringRequest(
           Request.Method.GET,
            url,
            {response ->
                val listType: Type = object : TypeToken<List<ChatObject?>?>(){}.type
                val chatMessages = Gson().fromJson<List<ChatObject>>(response, listType)

                succesCallback(chatMessages)
            },
            {error ->
                errorCallback()
            }
        )
        queue.add(stringRequest)
    }

    fun sendChatMessage(
        requestQueue: RequestQueue, chatObject: ChatObject, callback: (Boolean) -> Unit){
        val url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/sendMessage"
        val chatJson = Gson().toJson(chatObject)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(chatJson),
            {jsonObjectResponse ->
                callback(true)
            },
            {error ->
                callback(false)
            }

        )
        requestQueue.add(request)
    }
}