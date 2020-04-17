package com.example.googlenews.ui.main.adapter

import android.os.Build
import android.text.Html
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.googlenews.R
import com.example.googlenews.model.Articles
import com.example.googlenews.util.DateHelper

class GoogleNewsAdapter(itemListener: OnItemPositionClickListener) :
    RecyclerView.Adapter<GoogleNewsAdapter.NewsViewHolder>(), Filterable {

    private var mItemListener: OnItemPositionClickListener
    private var newsList = ArrayList<Articles>()
    private var newsListFiltered = ArrayList<Articles>()

    init {
        newsListFiltered = newsList
        mItemListener = itemListener
    }

    fun setNewsList(newsData: ArrayList<Articles>) {
        newsList.clear()
        newsList.addAll(newsData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.news_items, parent, false)
        return NewsViewHolder(view, mItemListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.bind(newsList[position])
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val key = constraint.toString()
                val results = FilterResults()
                if (constraint!!.isNotEmpty()) {
                    val filterList = ArrayList<Articles>()
                    for (item: Articles in newsListFiltered) {
                        if (item.title?.toLowerCase()?.contains(key.toLowerCase())!!) {
                            filterList.add(item)
                        }
                    }
                    results.count = filterList.size
                    results.values = filterList
                } else {
                    results.count = newsListFiltered.size
                    results.values = newsListFiltered
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                newsList = results?.values as ArrayList<Articles>
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemPositionClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    inner class NewsViewHolder(itemView: View, itemListener: OnItemPositionClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_title)
        var content: TextView = itemView.findViewById(R.id.tv_description)
        var date: TextView = itemView.findViewById(R.id.tv_date)
        var imgUser: ImageView = itemView.findViewById(R.id.img_user)
        var container: CardView = itemView.findViewById(R.id.container)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(newsItem: Articles) {
            title.text = Html.fromHtml(newsItem.title)
            content.text = Html.fromHtml(newsItem.description)
            date.text = DateHelper.convertToDate(newsItem.publishedAt)
            container.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.fade_transition_animation)
            imgUser.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.fade_scale_anim)
            Glide.with(imgUser.context)
                .asBitmap().transform(CircleCrop())
                .load(newsItem.urlToImage)
                .into(imgUser)
            content.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        init {
            itemView.setOnClickListener {
                itemListener.onItemClick(adapterPosition)
            }

            itemView.setOnLongClickListener {
                itemListener.onItemLongClick(adapterPosition)
                true
            }
        }
    }
}