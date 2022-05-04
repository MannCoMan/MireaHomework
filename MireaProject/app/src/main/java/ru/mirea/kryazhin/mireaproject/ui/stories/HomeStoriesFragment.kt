package ru.mirea.kryazhin.mireaproject.ui.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.mirea.kryazhin.mireaproject.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeStoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeStoriesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var fab_add: FloatingActionButton? = null
    var recyclerView: RecyclerView? = null
    var stories: List<Stories>? = null
    var buttonSave: Button? = null

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
        val view: View = inflater.inflate(R.layout.fragment_home_stories, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)

        stories = getListData()
        recyclerView?.setAdapter(RecyclerViewAdapter(requireActivity(), stories!!))

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView?.setLayoutManager(linearLayoutManager)


        return view
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab_add = view.findViewById(R.id.fab_add)
        fab_add?.setOnClickListener(View.OnClickListener { view -> findNavController(view).navigate(R.id.action_nav_stories_to_storiesFragment) })
        make_stories()
    }

    private fun make_stories() {
        Toast.makeText(context, "make_stories", Toast.LENGTH_SHORT).show()
    }

    private fun getListData(): List<Stories>? {
        val list: MutableList<Stories> = ArrayList()
        val stories1 = Stories("Подпись к фото", "stories_1")
        val stories2 = Stories("Подпись к фото", "stories_2")
        val stories3 = Stories("Подпись к фото", "stories_3")
        list.add(stories1)
        list.add(stories2)
        list.add(stories3)
        return list
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeStoriesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeStoriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}