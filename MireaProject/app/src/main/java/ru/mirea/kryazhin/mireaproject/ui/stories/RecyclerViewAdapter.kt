package ru.mirea.kryazhin.mireaproject.ui.stories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.kryazhin.mireaproject.BuildConfig
import ru.mirea.kryazhin.mireaproject.R


class RecyclerViewAdapter(context: Context, private val stories: List<Stories>) :
    RecyclerView.Adapter<RecyclerViewHolder>() {
    private val context: Context
    private val layoutInflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View = layoutInflater.inflate(R.layout.item_stories, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val stories = stories[position]
        val imageResID = getDrawableResIdByName(stories.photo)
        holder.photo.setImageResource(imageResID)
        holder.text.text = stories.text
    }

    private fun getDrawableResIdByName(photo: String): Int {
        val pkgName: String = BuildConfig.APPLICATION_ID
        return context.getResources().getIdentifier(photo, "drawable", pkgName)
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    init {
        this.context = context
        layoutInflater = LayoutInflater.from(context)
    }
}