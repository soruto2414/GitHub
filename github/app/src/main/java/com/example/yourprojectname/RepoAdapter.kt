package com.example.yourprojectname

import android.os.Build.VERSION_CODES.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R
import java.net.URL

class RepoAdapter(private val onRepoClick: (URL) -> Unit) : PagingDataAdapter<Repo, RepoAdapter.RepoViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        if (repo != null) {
            holder.nameTextView.text = repo.name
            //holder.descriptionTextView.text = repo.description
        }
    }

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.recycler_view)
        //val descriptionTextView: TextView = view.findViewById(R.id.repo_description)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }
}