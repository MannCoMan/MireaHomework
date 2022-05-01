package ru.mirea.kryazhin.mireaproject.ui.musicplayer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.mirea.kryazhin.mireaproject.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MusicPlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MusicPlayerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var playButton: Button? = null
    var musicPlay = false

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
        val view: View = inflater.inflate(R.layout.fragment_music_player, container, false)
        playButton = view.findViewById<Button>(R.id.buttonPlayer)
        playButton?.setOnClickListener(View.OnClickListener { view -> PlayOrStopMusic(view) })
        return view
    }

    fun onClickPlayMusic(view: View?) {
        requireActivity().startService(
            Intent(activity, PlayerService::class.java)
        )
    }

    fun onClickStopMusic(view: View?) {
        requireActivity().stopService(
            Intent(activity, PlayerService::class.java)
        )
    }

    fun PlayOrStopMusic(view: View?) {
        if (!musicPlay) {
            onClickPlayMusic(view)
            musicPlay = true
            playButton!!.text = "Stop"
        } else {
            onClickStopMusic(view)
            musicPlay = false
            playButton!!.text = "Play"
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MusicPlayerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MusicPlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}