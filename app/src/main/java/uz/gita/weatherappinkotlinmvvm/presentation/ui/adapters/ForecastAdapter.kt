package uz.gita.weatherappinkotlinmvvm.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import uz.gita.weatherappinkotlinmvvm.data.common.forecast.HourData
import uz.gita.weatherappinkotlinmvvm.databinding.ItemForecastBinding

class ForecastAdapter : Adapter<ForecastAdapter.ItemHolder>() {

    private var list: List<HourData> = ArrayList()

    fun setData(l: List<HourData>) {
        list = l
        notifyDataSetChanged()
    }

    inner class ItemHolder(private val binding: ItemForecastBinding) :
        ViewHolder(binding.root) {

        fun bind() {
            binding.apply {
                val time = list[adapterPosition].time

                txtTime.text = time.substring(11, time.length)
                txtTemperature.text = list[adapterPosition].temp_c.toInt().toString() + "℃"

                val url = "https:${list[adapterPosition].condition.icon}"
                Glide.with(binding.root.context).load(url).into(imgIcon)
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