package by.senla.injector

import by.senla.injector.annotations.Inject
import by.senla.injector.interfaces.Module
import java.lang.Exception
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException

class InjectorFramework(
    private val module: Module
) {

    @Throws(Exception::class)
    fun inject(classToInject: Class<*>?): Any? {
        return classToInject?.let { injectFieldsIntoClass(it) }
    }

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        InvocationTargetException::class,
        NoSuchMethodException::class
    )
    private fun injectFieldsIntoClass(classToInject: Class<*>): Any? {
        for (constructor in classToInject.constructors) {
            return if (constructor.isAnnotationPresent(Inject::class.java)) {
                injectFieldsViaConstructor(classToInject, constructor)
            } else {
                injectFields(classToInject)
            }
        }
        return null
    }

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        InvocationTargetException::class,
        NoSuchMethodException::class
    )
    private fun injectFields(classToInject: Class<*>): Any {
        val o = classToInject.newInstance()
        for (field in classToInject.declaredFields) {
            if (field.isAnnotationPresent(Inject::class.java)) {
                val dependency: Class<*> = module.takeMapping(field.type)
                field.isAccessible = true
                field[o] = dependency.getConstructor().newInstance()
            }
        }
        return o
    }

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        InvocationTargetException::class,
        NoSuchMethodException::class
    )
    private fun injectFieldsViaConstructor(
        classToInject: Class<*>,
        constructor: Constructor<*>
    ): Any {
        val parameterTypes: Array<Class<*>> = constructor.parameterTypes
        val objArr = arrayOfNulls<Any>(parameterTypes.size)
        var i = 0
        for (c in parameterTypes) {
            val dependency: Class<*> = module.takeMapping(c)
            if (c.isAssignableFrom(dependency)) {
                objArr[i++] = dependency.getConstructor().newInstance()
            }
        }
        return classToInject.getConstructor(*parameterTypes).newInstance(*objArr)
    }
}
