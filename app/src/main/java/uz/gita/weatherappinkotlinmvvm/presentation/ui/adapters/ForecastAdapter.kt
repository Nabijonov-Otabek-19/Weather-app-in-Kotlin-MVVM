package uz.gita.weatherappinkotlinmvvm.presentation.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.gita.weatherappinkotlinmvvm.data.common.forecast.ForecastdayData
import uz.gita.weatherappinkotlinmvvm.databinding.ItemForecastBinding

class ForecastAdapter : Adapter<ForecastAdapter.ItemHolder>() {

    private var list = ArrayList<ForecastdayData>()

    fun setData(l: List<ForecastdayData>){
        list.clear()
        list.addAll(l)
        notifyDataSetChanged()
    }

    inner class ItemHolder(private val binding: ItemForecastBinding) :
        ViewHolder(binding.root) {

        fun bind() {
            binding.apply {
                txtTime.text = list[adapterPosition].hour[adapterPosition].time
                txtTemperature.text = list[adapterPosition].hour[adapterPosition].temp_c.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind()
    }
}