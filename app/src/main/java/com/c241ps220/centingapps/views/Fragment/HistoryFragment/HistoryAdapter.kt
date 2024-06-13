package com.c241ps220.centingapps.views.Fragment.HistoryFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c241ps220.centingapps.Repository.ChildDiffCallback
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.databinding.ItemChildBinding
import com.c241ps220.centingapps.utils.CustomFunction

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    private val listChild = ArrayList<Child>()

    fun setListChild(listChild: List<Child>) {
        val diffCallback = ChildDiffCallback(this.listChild, listChild)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listChild.clear()
        this.listChild.addAll(listChild)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listChild.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listChild[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listChild[position])
        }
    }

    class HistoryViewHolder(private val binding: ItemChildBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(child: Child) {
            with(binding){
                tvName.text = child.name
                tvGender.text = child.gender
                tvInisial.text = CustomFunction.getInitials(child.name)

            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Child)
    }

}