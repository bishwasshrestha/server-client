package com.example.bishwasshrestha.chatapp_client

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toolbar
import com.example.bishwasshrestha.chatapp_client.ConnectToServer
import com.example.bishwasshrestha.chatapp_client.R.id.tool_bar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(),ClientObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       setSupportActionBar(tool_bar)

        thread{ConnectToServer.run() }

        login_btn.setOnClickListener {
            if(!username.text.isEmpty()){
                val user =username.text.toString()
                App.user = user
                ConnectToServer.sendtoserver(":user "+ user)
                Log.d("Key", user)
                val intent = Intent(this@MainActivity, ChatActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Enter a username", Toast.LENGTH_SHORT).show()
            }
            username.text.clear()
        }
    }

    override fun update(text: String) {

    }
}
