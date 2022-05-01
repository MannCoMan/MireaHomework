package ru.mirea.kryazhin.loadermanger

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader

class MainActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<String> {
    val TAG = this.javaClass.simpleName
    private val LoaderID = 1234
    var editText: EditText? = null
    var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
        textView = findViewById(R.id.textView)
    }

    private val string: String
        private get() = editText!!.text.toString()

    override fun onLoaderReset(loader: Loader<String>) {
        Log.d(TAG, "onLoaderReset")
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): MyLoader {
        if (i == LoaderID) {
            Toast.makeText(this, "onCreateLoader:$i", Toast.LENGTH_SHORT).show()
            return MyLoader(this, bundle)
        }
        return MyLoader(this,null)
    }
    override fun onLoadFinished(loader: Loader<String>, s: String) {
        if (loader.id == LoaderID) {
            Log.d(TAG, "onLoadFinished$s")
            Toast.makeText(this, "onLoadFinished:$s", Toast.LENGTH_SHORT).show()
            textView!!.text = s
        }
    }

    fun ClickBtn(view: View?) {
        val loader: Loader<String>
        val bundle = Bundle()
        bundle.putString(MyLoader.ARG_WORD, string)
        loader = supportLoaderManager.restartLoader(LoaderID, bundle, this)
        loader.forceLoad()
    }

    companion object {
        var lastCheckedId = 0
    }
}