package ru.mirea.kryazhin.mireaproject.ui.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import ru.mirea.kryazhin.mireaproject.R
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoriesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var random: Random? = null
    var masPhoto = arrayOf<Int>(
        R.drawable.img,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3
    )
    var makePhotoStories: Button? = null
    var buttonSave: Button? = null
    var imageViewStories: ImageView? = null
    var text: TextView? = null
    var pickedPhoto = 0
    var stories: List<Stories>? = null
    var list: MutableList<Stories>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Toast.makeText(getContext(), "There you make stories", Toast.LENGTH_SHORT).show();
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stories, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makePhotoStories = requireActivity().findViewById(R.id.buttonMakePhoto)
        imageViewStories = requireActivity().findViewById(R.id.imageViewStories)
        text = requireActivity().findViewById(R.id.text)
        buttonSave = requireActivity().findViewById(R.id.buttonSave)
        random = Random()
        makePhotoStories?.setOnClickListener(View.OnClickListener {
            pickedPhoto = random!!.nextInt(masPhoto.size)
            imageViewStories?.setImageResource(masPhoto[pickedPhoto])
        })
        buttonSave?.setOnClickListener(View.OnClickListener {
            val s = text?.getText().toString()
            val stories = Stories(s, "stories_1")
            list?.add(stories)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StoriesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StoriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}