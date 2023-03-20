package com.example.githubrepositories.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubrepositories.R
import com.example.githubrepositories.model.Item
import com.example.githubrepositories.model.ItemAction

class ReposListAdapter(var items: List<Item>) :
    RecyclerView.Adapter<ReposListAdapter.RepoViewHolder>() {

    var itemClickListener : ItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RepoViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_list,
            parent,
            false
        )
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReposListAdapter.RepoViewHolder, position: Int) {
        val listItemObject = items[position]
        with(holder) {
            title.text = listItemObject.name
            author.text = "Owner: ${listItemObject.owner.login}"
            img.load(listItemObject.owner.avatar_url)
            container.setOnClickListener {
                val action = ItemAction(
                    listItemObject.name,
                    listItemObject.full_name,
                    listItemObject.owner.login,
                    listItemObject.owner.url,
                    listItemObject.owner.avatar_url,
                    listItemObject.html_url,
                    listItemObject.description,
                    listItemObject.created_at,
                    listItemObject.visibility,
                    listItemObject.topics
                )
                itemClickListener?.onItemClicked(action)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class RepoViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_title)
        val author : TextView = view.findViewById(R.id.item_author)
        val img : ImageView = view.findViewById(R.id.item_img)
        val container : View = view.rootView
    }

    interface ItemClickListener {
        fun onItemClicked(action: ItemAction)
    }

}