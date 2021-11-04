package by.senla.injector.interfaces

interface Module {
    fun configure()
    fun <T> takeMapping(type: Class<T>): Class<out T>
}
