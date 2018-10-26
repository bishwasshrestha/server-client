package com.example.bishwasshrestha.chatapp_client

import java.text.SimpleDateFormat
import java.util.*

object DateUtils{

    fun dateandtime(millis:Long):String{
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(millis)

    }
}