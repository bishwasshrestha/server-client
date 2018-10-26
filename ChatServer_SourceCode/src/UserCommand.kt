
//BISHWAS SHRESTHA (ID- 1706204)
/*This is a singleton. It lists all the users in a mutable list of string. it has one function that takes username as argument
* it also returns a string*/

object UserCommand {

    var users = mutableMapOf<Int, String>()

    fun userCommandInterpret(port:Int, HashSet:String): String{
       if(users.containsKey(port)){
           users.replace(port, HashSet)     //One terminal is considered as one user so uer name can be changed
       }
        else{

           users.put(port, HashSet) //adds user name

       }

        return HashSet
    }

    fun deleteUser(port:Int, user:String){
        users.remove(port, user)
    }
}