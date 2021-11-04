package by.senla.lesson_07

import android.util.Log

class PrinterImpl: Printer {
    override fun print(text: String) {
        Log.e("TAG", text)
    }
}
