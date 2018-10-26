package com.example.bishwasshrestha.chatapp_client

import java.util.*

interface ClientObservable {

    fun registerObserver(observer:ClientObserver)

    fun deleteObserver(observer:ClientObserver)

    fun notifyObserver(message:String)
}