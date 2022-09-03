package com.skapps.YksStudyApp.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skapps.YksStudyApp.Model.HistoryPomodoro
import com.skapps.YksStudyApp.databinding.AddpomodorocardBinding

class HistoryPomodoroAdapter(var historyList:ArrayList<HistoryPomodoro>) :RecyclerView.Adapter<HistoryPomodoroAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(val recyclerRowBinding:AddpomodorocardBinding):RecyclerView.ViewHolder(recyclerRowBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val recyclerRowBinding=AddpomodorocardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryViewHolder(
            recyclerRowBinding
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
            val time = historyList[position].time.toString()
        holder.recyclerRowBinding.time.text="$time dk"
        holder.recyclerRowBinding.activity.text=historyList[position].activity
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
    fun updatePomodoro(newPomodoroList:List<HistoryPomodoro>){
        historyList.clear()
        historyList.addAll(newPomodoroList)
        notifyDataSetChanged()
    }
}