package ru.mirea.kryazhin.mireaproject.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.mirea.kryazhin.mireaproject.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeSettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeSettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var fab_settings: FloatingActionButton? = null
    private var preferences: SharedPreferences? = null
    var tv_signature: TextView? = null
    var tv_reply: TextView? = null
    var tv_sync: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_settings, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_signature = requireView().findViewById(R.id.tv_signature)
        tv_reply = requireView().findViewById(R.id.tv_reply)
        tv_sync = requireView().findViewById(R.id.tv_sync)
        fab_settings = requireView().findViewById(R.id.fab_settings)
        fab_settings?.setOnClickListener(View.OnClickListener { view ->
            findNavController(view).navigate(
                R.id.action_nav_settings_to_settingsFragment
            )
        })
        settings()
    }

    private fun settings() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val signature = preferences?.getString("signature", "")
        val defaultReplyAction = preferences?.getString("reply", "")
        val sync = preferences?.getBoolean("sync", true)
        tv_signature!!.text = "Signature: $signature"
        tv_reply!!.text = "Default reply: $defaultReplyAction"
        tv_sync!!.text = "Sync: $sync"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeSettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}