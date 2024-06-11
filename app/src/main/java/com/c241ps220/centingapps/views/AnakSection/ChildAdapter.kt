package com.c241ps220.centingapps.views.AnakSection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.databinding.ItemChildBinding

class ChildAdapter(private val onClick: (Child) -> Unit) :
    ListAdapter<Child, ChildAdapter.ChildViewHolder>(ChildDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ItemChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val child = getItem(position)
        holder.bind(child)
    }

    inner class ChildViewHolder(private val binding: ItemChildBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(child: Child) {
            binding.tvName.text = child.name
            binding.tvGender.text = child.birthDate
            binding.root.setOnClickListener { onClick(child) }
        }
    }

    class ChildDiffCallback : DiffUtil.ItemCallback<Child>() {
        override fun areItemsTheSame(oldItem: Child, newItem: Child): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Child, newItem: Child): Boolean {
            return oldItem == newItem
        }
    }
}
