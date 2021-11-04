package by.senla.lesson_07

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import by.senla.injector.Injector
import by.senla.injector.impl.AbstractModule

class App : Application() {

    var injectionClient: InjectionClient? = null

    override fun onCreate() {
        super.onCreate()
        val injector = Injector.getFramework(Configurator())
        injectionClient = injector.inject(InjectionClient::class.java) as InjectionClient
    }
}
