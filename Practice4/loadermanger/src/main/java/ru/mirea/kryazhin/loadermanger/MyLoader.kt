package ru.mirea.kryazhin.loadermanger

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import androidx.loader.content.AsyncTaskLoader
import java.util.*

class MyLoader(context: Context, args: Bundle?) : AsyncTaskLoader<String?>(context) {
    private var mixString: String? = null
    override protected fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): String {
        // emulate long-running operation
        SystemClock.sleep(3000)
        return MyLoader.Companion.mix(mixString)
    }

    companion object {
        const val ARG_WORD = "word"
        fun mix(str: String?): String {
            var str = str
            val charList: ArrayList<Char?>
            charList = ArrayList()
            if (str != null) {
                for (symbol in str.toCharArray()) {
                    charList.add(symbol)
                }
            }
            Collections.shuffle(charList)
            str = charList.toString().replace("^\\[|\\]$".toRegex(), "")
            str = str.replace(",", "")
            return str
        }
    }

    init {
        if (args != null) mixString = args.getString(MyLoader.Companion.ARG_WORD)
    }
}

