
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.lang.reflect.Array.get
import java.util.*

//BISHWAS SHRESTHA (ID- 1706204)
/*This class does all the scanning and printing through out the chat server. It also interprets the command and calls the
* required class. It interfaces with observable class */

class CommandInterpreter(input: InputStream, output: OutputStream, Port:Int): Runnable, ChatObserver{
    val port = Port
    val scanner = Scanner(input)
    val printer = PrintStream(output, true)
    var user = UserCommand
    var message = MessageCommand
   var userName=""     //username shall be used through out this class, which is why, it has been given a global variable.
    private set

    override fun run() {

        MessageCommand.registerObserver(this)
        //registers observer in MessageCommand which is an observable

        var quit = false
        printer.println("welcome to the Chat Server")
        while (!quit) {         //runs an infinite loop

            val fullcommand = scanner.nextLine()
            //splits the input into command and/or username and assigns in splitcommand array
            val splitcommand = fullcommand.split(" ")
            val command = splitcommand.getOrNull(0) ?: ""     //assigns the value in first index as command
            val suffix = splitcommand.getOrNull(1) ?: ""     //assigns the value in second index as username

            if (!quit) {                 //if the input is not empty and doesn't equal quit
                //if command portion of input starts with ":" it is command,  else it is a message
                if (command.startsWith(":")) {
                    when (command) {                            //when command is not empty

                        ":user" -> {                 //registers users
                            userName= user.userCommandInterpret(port, suffix)
                            /* this method call takes separated username to UserCommand class and adds it to
                            users list and because, once this loop has ended, "suffix" will be refreshed and it will lose its value
                            therefore this command returns the username back for further process. Port is used to make sure one terminal doesn't get more than one username  */
                            printer.println("User name set to $userName")
                        }

                        ":users" -> {       //displays active users
                            for (s in UserCommand.users)      //for every s in users in UserCommand class, it prints s.
                                printer.println(s.value)          //prints the user's names
                        }

                        ":message" -> {                     //calls the history of messages
                            //for every items in text from MessageCommand class, it returns value in s
                            for (s in MessageCommand.text){
                                printer.println(s.value + " by " + s.key)
                            }
                        }


                        ":quit" -> {
                            quit = !quit

                            UserCommand.deleteUser(port,userName)
                            //the user is removed from list as they sign out.

                            MessageCommand.deleteObserver(this)
                            //once the user has quit the conversation, it deletes user from observer list
                        }

                        else ->{
                            printer.println("Command not found")
                        }
                    }
                }
                else {
                    //this if-statement returns true only if user is currently listed on the user list.
                    // returns false if the user had exited the conversation
                    if (user.users.containsValue(userName)) {
                        //sends full input and user name to MessageCommands interpret method.
                        message.interprete(fullcommand.toString(), userName)

                    } else {
                        //this else statement prints if user tries to enter message before registering the username
                        printer.println("Please register a User Name first.")
                    }
                }
            }else printer.println("Please enter a command")  //prints this command if input is not valid.
        }
        println("closing scanner")
        scanner.close()     //ends the scanner.
    }

    override fun update(chat:String){
        printer.println(chat)
    }

}
