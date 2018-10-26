
//BISHWAS SHRESTHA (ID- 1706204)
// this is an observable Interface that registers users as observers and deletes observer once is offline. also notifies
//the observer with recent updates.
interface ChatObservable{

    fun registerObserver(observer:ChatObserver)

    fun deleteObserver(observer:ChatObserver)

    fun notifyObservers(message:String)
}