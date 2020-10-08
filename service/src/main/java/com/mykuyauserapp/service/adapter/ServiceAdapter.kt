package com.mykuyauserapp.service.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mykuyauserapp.data.repo.Service
import com.mykuyauserapp.service.R
import kotlinx.android.synthetic.main.item_service.view.*
import kotlin.math.min
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class ServiceAdapter() : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {
    var items: List<Service> = mutableListOf()
    var isExpanded = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder =
        ServiceViewHolder.Service(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_service, parent, false
            )
        )

    override fun getItemCount(): Int = if (isExpanded) items.count() else items.take(min(9, items.count())).count()

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        when (holder) {
            is ServiceViewHolder.Service -> {
                with(holder.itemView) {
                    val item = items[position]
                    textServiceItemTitle.text = item.title
                    Glide.with(imageServiceItemIcon).load(context.getImage(item.icon)).into(imageServiceItemIcon)
                }
            }
        }

    }


    sealed class ServiceViewHolder(view: View): RecyclerView.ViewHolder(view) {
        class Service(view: View): ServiceViewHolder(view)
    }
}

fun Context.getImage(value: String) = resources.getIdentifier(value, "drawable", packageName)
