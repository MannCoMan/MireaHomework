package ru.mirea.kryazhin.mireaproject.ui.stories

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.kryazhin.mireaproject.R


class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var photo: ImageView
    var text: TextView

    init {
        photo = itemView.findViewById(R.id.photo)
        text = itemView.findViewById(R.id.text)
    }
}