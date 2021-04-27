package com.example.chattes.ui.Chat

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chattes.Database.ChatObject


class ChatAdapter(
    private var dataSet: List<ChatObject>): RecyclerView.Adapter<ChatAdapter.ChatViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = ChatBubbleView(parent.context)

        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ChatViewHolder(view)
    }


    override fun getItemCount(): Int {
         return dataSet.size
    }


    fun updateData (newdata: List<ChatObject>){
        dataSet = newdata
        notifyDataSetChanged()
    }


    fun addInstance (chatObject: ChatObject){
        updateData(dataSet+chatObject)
    }


    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatObject = dataSet[position]

        holder.view.setChatText(chatObject.message)
        holder.view.setAuthorText(chatObject.userName.capitalize())

        holder.view.setSelfAuthor(chatObject.userId=="ln2lceAzGo")
    }


    inner class ChatViewHolder(
        val view: ChatBubbleView
    ) : RecyclerView.ViewHolder(view)
}