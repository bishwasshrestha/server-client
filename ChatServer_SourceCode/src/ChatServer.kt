import java.net.ServerSocket

//BISHWAS SHRESTHA (ID- 1706204)
/*This is chat server class*/


class ChatServer{

    fun serve(){

        try {
            val serverSocket = ServerSocket(5265, 7)    //allocates given port and holds three users in queue
            println("We have a port " + serverSocket.localPort)     //prints the local port on the console


            while (true) {
               val ss = serverSocket.accept()
                println("new connection " + ss.inetAddress.hostAddress + " " + ss.port)

                ChatConsole().run()

              val obj = Thread(CommandInterpreter(ss.getInputStream(), ss.getOutputStream(), ss.port))
                obj.start() // overrides the run method of CommandInterpreter

            }

        } catch (e: Exception) {    //catches any exception

            println("got exception: ${e.message}")
        } finally {           //Once all business is concluded it executes this portion
            println("All serving done.")
        }
    }
}