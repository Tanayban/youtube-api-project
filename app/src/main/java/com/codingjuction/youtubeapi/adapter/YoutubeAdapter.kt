package com.codingjuction.youtubeapi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codingjuction.youtubeapi.R
import com.codingjuction.youtubeapi.models.YoutubeResponse
import com.codingjuction.youtubeapi.ui.DisplayActivity


class YoutubeAdapter(val context: Context): RecyclerView.Adapter<YoutubeAdapter.VideoViewHolder>() {
    private val items: MutableList<YoutubeResponse.Item> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.video_item_adapter, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val youtubeResponse = items[position].id
        val youtubeResponse2 = items[position].statistics
        val snippet = items[position].snippet!!
        val url = snippet.thumbnails!!.default!!.url!!
        Glide.with(holder.itemView.context).load(url).centerCrop().into(holder.logo)
        holder.title.setText(snippet.title)
        holder.description.setText(snippet.description)
        holder.date.setText(snippet.publishedAt)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DisplayActivity::class.java)
            intent.putExtra("title", snippet.title)
            intent.putExtra("description", snippet.description)
            intent.putExtra("url", youtubeResponse!!.videoId)
            context.startActivity(intent)
        }
    }

    private var onItemClickListener: ((one: String, two: String) -> Unit)? = null

    fun setOnItemClickListerner(listener: (one: String, two: String) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addAll(items: Collection<YoutubeResponse.Item>) {
        val currentItemCount = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(currentItemCount, items.size)
    }

    fun replaceWith(items: Collection<YoutubeResponse.Item>?) {
        if (items != null) {
            val oldCount = this.items.size
            val newCount = items.size
            val delCount = oldCount - newCount
            this.items.clear()
            this.items.addAll(items)
            if (delCount > 0) {
                notifyItemRangeChanged(0, newCount)
                notifyItemRangeRemoved(newCount, delCount)
            } else if (delCount < 0) {
                notifyItemRangeChanged(0, oldCount)
                notifyItemRangeInserted(oldCount, -delCount)
            } else {
                notifyItemRangeChanged(0, newCount)
            }
        }
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var logo: ImageView
        var title: TextView
        var description: TextView
        var date: TextView

        init {
            logo = itemView.findViewById(R.id.logo)
            title = itemView.findViewById(R.id.title)
            description = itemView.findViewById(R.id.description)
            date = itemView.findViewById(R.id.date)
        }
    }
}