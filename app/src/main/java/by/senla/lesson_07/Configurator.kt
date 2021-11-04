package by.senla.lesson_07

import by.senla.injector.impl.AbstractModule

class Configurator : AbstractModule() {
    override fun configure() {
        createMapping(Printer::class.java, PrinterImpl::class.java)
    }
}
