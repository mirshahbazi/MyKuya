package com.mykuyauserapp.service.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mykuyauserapp.data.repo.News
import com.mykuyauserapp.service.R
import kotlinx.android.synthetic.main.item_news.view.*
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var items: List<News> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_news, parent, false
        ))

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]
        with(holder.itemView) {
            textNewsItemTitle.text = item.title
            textNewsItemDescription.text = item.description
            Glide.with(imageNewsItemHeader).load(context.getImage(item.banner)).into(imageNewsItemHeader)
        }
    }

    class NewsViewHolder(view: View): RecyclerView.ViewHolder(view)
}