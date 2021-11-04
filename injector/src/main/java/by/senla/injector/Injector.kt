package by.senla.injector

import by.senla.injector.interfaces.Module

object Injector {
    fun getFramework(module: Module): InjectorFramework {
        module.configure()
        return InjectorFramework(module)
    }
}
