package com.example.bishwasshrestha.chatapp_client

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.bishwasshrestha.chatapp_client.R.layout.abc_screen_toolbar
import com.example.bishwasshrestha.chatapp_client.R.layout.chatactivity_layout
import kotlinx.android.synthetic.main.chatactivity_layout.*
import kotlin.concurrent.thread

class ChatActivity :  AppCompatActivity(), ClientObserver {

   private var adapter : MyAdapter = MyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(chatactivity_layout)
        tool_bar.setTitle(App.user)
        setSupportActionBar(tool_bar)
        tool_bar.setLogo(R.mipmap.ic_launcher_round)

        ConnectToServer.registerObserver(this)

        messageList.layoutManager = LinearLayoutManager(this)
        messageList.adapter = adapter

       send_btn.setOnClickListener {
            if (!message_in.text.isEmpty()) {
                   ConnectToServer.sendtoserver(message_in.text.toString())
                    message_in.text.clear()
            }
       }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.log_out ->{
                ConnectToServer.deleteObserver(this)
                ConnectToServer.sendtoserver(":quit")
                val intent= Intent(this@ChatActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun update(text: String) {
        val message = Message(getName(text), getMessage(text), System.currentTimeMillis())
       runOnUiThread{
                adapter.addMessage(message)
                messageList.scrollToPosition(adapter.itemCount-1)
            }
    }

    private fun getMessage(text:String):String{
       return text.substringBefore("BY")

    }
    private fun getName(text:String):String{
        return text.substringAfter("BY ")

    }


}
