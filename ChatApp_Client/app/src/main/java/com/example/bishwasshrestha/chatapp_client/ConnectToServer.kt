
package com.example.bishwasshrestha.chatapp_client

import android.util.Log
import com.example.bishwasshrestha.chatapp_client.ClientObservable
import com.example.bishwasshrestha.chatapp_client.ClientObserver
import org.jetbrains.anko.doAsync
import java.io.*
import java.net.Socket
import java.util.*
import kotlin.concurrent.thread


object ConnectToServer: ClientObservable, Runnable {

    private var observers = mutableListOf<ClientObserver>()
    lateinit var socket:Socket

    override fun run() {
        try {
            socket = Socket("10.0.2.2", 5265)
            Log.d("Key", "Connected to " + socket.inetAddress + socket.port)
            while (true) {
                readfromServer()
            }
        }catch (e:IOException) {
            Log.d("Key", "Caught exception ${e.message}")
        }
    }


     fun readfromServer():String {
         val input = this.socket.getInputStream()
         val scanner = Scanner(input)
         var text:String =""
         while(true){
             try{
                 text = scanner.nextLine().toString()
                 notifyObserver(text)

             }catch (e:Exception){
                 break
             }
         }
         return text
     }

    fun sendtoserver(text_input: String) {
        val printer = PrintStream(this.socket.getOutputStream(), true)
        thread { printer.println(text_input) }
    }

    override fun registerObserver(observer: ClientObserver) {
       observers.add(observer)
    }

    override fun deleteObserver(observer: ClientObserver) {
        observers.remove(observer)
    }

    override fun notifyObserver(message: String) {
        for(i in observers){
            i.update(message)
        }
    }
}