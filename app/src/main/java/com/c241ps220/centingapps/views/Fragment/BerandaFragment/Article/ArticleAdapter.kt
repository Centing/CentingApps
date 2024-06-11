package com.c241ps220.centingapps.views.Fragment.BerandaFragment.Article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c241ps220.centingapps.R
import com.c241ps220.centingapps.utils.CustomFunction
import com.c241ps220.centingapps.views.Fragment.BerandaFragment.Article.Article


class ArticleAdapter (private val listArticle: ArrayList<Article>) : RecyclerView.Adapter<ArticleAdapter.ListViewHolder>()  {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_article, parent, false)
        return ListViewHolder(view)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvImage: ImageView = itemView.findViewById(R.id.tvImage)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvPublisher: TextView = itemView.findViewById(R.id.tvPublisher)
        val tvTimelapse: TextView = itemView.findViewById(R.id.tvTimelapse)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Article)
    }

    override fun getItemCount(): Int = listArticle.size



    override fun onBindViewHolder(holder: ArticleAdapter.ListViewHolder, position: Int) {
        val (date, title, publisher, urlArticle, timelapse, image) = listArticle[position]
        Glide.with(holder.itemView.context)
            .load(image) // URL Gambar
            .into(holder.tvImage) // imageView mana yang akan diterapkan
        holder.tvDate.text = date
        holder.tvTitle.text = title
        holder.tvPublisher.text = publisher
        holder.tvTimelapse.text = CustomFunction.generateTimeLapse(date)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listArticle[holder.adapterPosition]) }

    }
}