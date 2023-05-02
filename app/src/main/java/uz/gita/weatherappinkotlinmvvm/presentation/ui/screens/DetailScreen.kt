package uz.gita.weatherappinkotlinmvvm.presentation.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import uz.gita.weatherappinkotlinmvvm.R
import uz.gita.weatherappinkotlinmvvm.data.common.current.CurrentData
import uz.gita.weatherappinkotlinmvvm.data.source.local.SharedPref
import uz.gita.weatherappinkotlinmvvm.databinding.ScreenDetailBinding
import uz.gita.weatherappinkotlinmvvm.presentation.ui.adapters.ForecastAdapter
import uz.gita.weatherappinkotlinmvvm.presentation.viewmodels.DetailViewModel
import uz.gita.weatherappinkotlinmvvm.presentation.viewmodels.impl.DetailViewModelImpl
import uz.gita.weatherappinkotlinmvvm.utils.CurrentLocation

class DetailScreen : Fragment(R.layout.screen_detail) {
    private val viewBinding by viewBinding(ScreenDetailBinding::bind)
    private val viewModel: DetailViewModel by viewModels<DetailViewModelImpl>()
    private val adapter by lazy { ForecastAdapter() }

    private val currentLocation by lazy { CurrentLocation.getInstance(requireActivity()) }
    private val sharedPref by lazy { SharedPref.getInstance(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.apply {
            recyclerForecast.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            recyclerForecast.adapter = adapter
        }

        //viewModel.loadWeather("Tashkent")
        viewModel.loadWeather(getLocation())
        viewModel.loadForecast(getLocation())

        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successObserver)
        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)

        viewModel.successForecastLiveData.observe(viewLifecycleOwner) {
            adapter.setData( it.forecastday[0].hour)
        }

        viewModel.errorForecastLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewBinding.refresh.setOnRefreshListener {
            viewModel.loadWeather(getLocation())
            viewModel.loadForecast(getLocation())
        }
    }

    private fun getLocation(): String {
        viewBinding.refresh.isRefreshing = true
        currentLocation.getCurrentLocation()
        viewBinding.refresh.isRefreshing = false
        return sharedPref.location
    }

    private val loadingObserver = Observer<Boolean> {
        val isVisibleLoading = if (it) View.VISIBLE else View.GONE
        val isVisible = if (!it) View.VISIBLE else View.GONE
        viewBinding.progress.visibility = isVisibleLoading
        viewBinding.linear.visibility = isVisible
        viewBinding.linear2.visibility = isVisible
        viewBinding.recyclerForecast.visibility = isVisible
    }

    private val successObserver = Observer { value: CurrentData ->
        Glide.with(requireContext())
            .load(value.condition.icon)
            .placeholder(R.drawable.ic_launcher_background)
            .into(viewBinding.imgIcon)

        viewBinding.apply {
            txtData.text = value.last_updated
            txtCity.text = value.locationData.name
            txtTemperature.text = value.temp_c.toInt().toString() + "℃"
            txtStatus.text = value.condition.text
            Glide.with(requireContext())
                .load("https:${value.condition.icon}")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imgIcon)
        }
    }

    private val errorObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
}