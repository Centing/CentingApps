package com.c241ps220.centingapps.views.History

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c241ps220.centingapps.Repository.ChildDiffCallback
import com.c241ps220.centingapps.Repository.ResultDiffCallback
import com.c241ps220.centingapps.data.database.result.DetectionResult
import com.c241ps220.centingapps.databinding.ItemChildBinding
import com.c241ps220.centingapps.databinding.ItemHistoryBinding
import com.c241ps220.centingapps.utils.CustomFunction

class HistoryActivityAdapter: RecyclerView.Adapter<HistoryActivityAdapter.HistoryActivityViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    private val listResultByChild = ArrayList<DetectionResult>()

    fun setListDetectionResult(listResultByChild: List<DetectionResult>) {
        val diffCallback = ResultDiffCallback(this.listResultByChild, listResultByChild)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listResultByChild.clear()
        this.listResultByChild.addAll(listResultByChild)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryActivityViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryActivityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listResultByChild.size
    }

    override fun onBindViewHolder(holder: HistoryActivityViewHolder, position: Int) {
        holder.bind(listResultByChild[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listResultByChild[position])
        }
    }

    class HistoryActivityViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: DetectionResult) {
            with(binding){
                tvDateDetect.text = result.childDetectionDate
                tvStatus.text = result.childDetectionResult
                tvAge.text = result.childAge
                tvHeightLatest.text = result.childLastHeight
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetectionResult)
    }

}