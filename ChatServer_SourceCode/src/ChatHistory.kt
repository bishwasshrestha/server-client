import java.text.SimpleDateFormat
import java.util.*
import javax.print.attribute.IntegerSyntax

//BISHWAS SHRESTHA (ID- 1706204)
/*it handles all the messages that user enters and stores it with time and date stamp, this could be called Chat History.
It accepts no arguments and has three functions that override the observable class functions and one extra function
that interprets the input message and stores it to the map of text. */

object MessageCommand : ChatObservable{

    //an object of the observer class
    var observers = mutableListOf<ChatObserver>()

    //map of String that stores username and their respective messages
    var text = mutableMapOf<String, String>()
    var chatter = arrayListOf<String>()

            //this functions takes username and full input from user input as argument and stores it for future reference.
    fun interprete(fullMessage:String, userName:String) {

        //date and time stamp.
        val timestamp= System.currentTimeMillis()
        val date = Date(timestamp)
        val dateString = SimpleDateFormat("yyyy-MM-dd").format(date)
        val timeString = SimpleDateFormat("HH:mm:ss.SSS").format(date)

        //stores username as  key and message with date and time string as value
        text.put(userName, fullMessage + " at " + dateString + " T " + timeString)

        //notifies observer list with new message
        notifyObservers(fullMessage + " by " + userName)
    }

    //adds users to observers list
    override fun registerObserver(observer: ChatObserver) {
        observers.add(observer)
    }

    //deletes observers from the list once they exit the terminal
    override fun deleteObserver(observer: ChatObserver) {
        observers.remove(observer)
    }

    //every time there is a news entry in the message array, it calls the update function in observer class and displayes
    //messages to their terminal.
    override fun notifyObservers(message:String) {
        for(i in observers){
            i.update(message)
        }
    }
}