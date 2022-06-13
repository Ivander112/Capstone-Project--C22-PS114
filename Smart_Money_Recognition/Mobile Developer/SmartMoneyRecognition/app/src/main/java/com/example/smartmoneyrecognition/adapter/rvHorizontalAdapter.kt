package com.example.smartmoneyrecognition.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmoneyrecognition.activity.TipsActivity
import com.example.smartmoneyrecognition.activity.UangDuniaActivity
import com.example.smartmoneyrecognition.activity.UangIndonesiaActivity
import com.example.smartmoneyrecognition.data_class.rvData
import com.example.smartmoneyrecognition.databinding.RvItemHorizontalBinding

class rvHorizontalAdapter(private val listRvData: ArrayList<rvData>) : RecyclerView.Adapter<rvHorizontalAdapter.ListViewHolder>() {
    class ListViewHolder(private val binding: RvItemHorizontalBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(images: Int, title: String,position: Int){
            binding.rvImage.setImageResource(images)
            binding.rvTitle.setText(title)
            itemView.setOnClickListener{
                when(position){
                    0 -> {
                        val intent = Intent(itemView.context, UangIndonesiaActivity::class.java)
                        itemView.context.startActivity(intent)
                    }
                    1 ->{
                        val intent = Intent(itemView.context, UangDuniaActivity::class.java)
                        itemView.context.startActivity(intent)
                    }
                    2->{
                        val intent = Intent(itemView.context, TipsActivity::class.java)
                        itemView.context.startActivity(intent)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = RvItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listRvData[position].image,listRvData[position].title,position)

    }

    override fun getItemCount() = listRvData.size
}