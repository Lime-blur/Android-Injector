package by.senla.injector.impl

import by.senla.injector.interfaces.Module
import java.lang.IllegalArgumentException

abstract class AbstractModule : Module {

    private val classMap: MutableMap<Class<*>, Class<*>> = HashMap()

    protected fun <T> createMapping(baseClass: Class<T>, subClass: Class<out T>) {
        classMap[baseClass] = subClass.asSubclass(baseClass)
    }

    override fun <T> takeMapping(type: Class<T>): Class<out T> {
        val implementation = classMap[type]
            ?: throw IllegalArgumentException("Couldn't find the mapping for: $type")
        return implementation.asSubclass(type)
    }
}
