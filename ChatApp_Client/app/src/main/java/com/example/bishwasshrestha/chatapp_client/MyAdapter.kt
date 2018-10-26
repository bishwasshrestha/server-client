package com.example.bishwasshrestha.chatapp_client

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.my_message_layout.view.*
import kotlinx.android.synthetic.main.others_message_layout.view.*
import org.w3c.dom.Text


const val VIEW_TYPE_MINE =1
const val VIEW_TYPE_OTHER =2

class MyAdapter(private val context:Context): RecyclerView.Adapter<MyViewHolder>()  {

     val messages:ArrayList<Message> = ArrayList()

    fun addMessage(message: Message){
        messages.add(message)
        notifyDataSetChanged()
    }

    inner class MyMessageView(view:View):MyViewHolder(view){
        var messageText:TextView = view.my_message_view
        var timeText:TextView = view.my_time_view
        override fun bind(message: Message) {
            messageText.text=message.message
            timeText.text = DateUtils.dateandtime(message.time)
        }
    }
    inner class OthersMessageView(view:View):MyViewHolder(view){
        var messageText:TextView = view.others_message_view
        var userText:TextView = view.others_user_id_view
        var time:TextView = view.others_time_view
        override fun bind(message:Message) {
            messageText.text=message.message
            userText.text = message.user
            time.text=DateUtils.dateandtime(message.time)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        Log.d("Check","${App.user}")
        Log.d("Check","${message.user}")

        return if(App.user == message.user){
            VIEW_TYPE_MINE
        }else{
            VIEW_TYPE_OTHER
        }
    }
    //default methods
    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

       return if(viewType==VIEW_TYPE_MINE) {
            MyMessageView(LayoutInflater.from(context).inflate(R.layout.my_message_layout, parent, false))
        }else{
            OthersMessageView(LayoutInflater.from(context).inflate(R.layout.others_message_layout, parent, false))
        }

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }
}
open class MyViewHolder(view:View):RecyclerView.ViewHolder(view){
    open fun bind(message:Message){}

}