import java.io.OutputStream
import java.io.PrintStream

//BISHWAS SHRESTHA (ID- 1706204)
/* this ChatConsole class prints the updated message from ChatServer on console*/
class ChatConsole: ChatObserver {

    fun run(){
        MessageCommand.registerObserver(this)

    }

    override fun update(text:String){
      println(text)

    }

}