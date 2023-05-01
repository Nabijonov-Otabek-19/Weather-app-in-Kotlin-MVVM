package uz.gita.weatherappinkotlinmvvm.presentation.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import uz.gita.weatherappinkotlinmvvm.R
import uz.gita.weatherappinkotlinmvvm.data.common.CurrentData
import uz.gita.weatherappinkotlinmvvm.data.source.local.SharedPref
import uz.gita.weatherappinkotlinmvvm.databinding.ScreenDetailBinding
import uz.gita.weatherappinkotlinmvvm.presentation.viewmodels.DetailViewModel
import uz.gita.weatherappinkotlinmvvm.presentation.viewmodels.impl.DetailViewModelImpl
import uz.gita.weatherappinkotlinmvvm.utils.CurrentLocation

class DetailScreen : Fragment(R.layout.screen_detail) {
    private val viewBinding by viewBinding(ScreenDetailBinding::bind)
    private val viewModel: DetailViewModel by viewModels<DetailViewModelImpl>()

    private val currentLocation by lazy { CurrentLocation.getInstance(requireActivity()) }
    private val sharedPref by lazy { SharedPref.getInstance(requireActivity()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        currentLocation.getCurrentLocation()
        val lat = sharedPref.location

        //viewModel.loadWeather("Tashkent")
        viewModel.loadWeather(lat)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successObserver)
        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
    }

    private val loadingObserver = Observer<Boolean> {
        val isVisibleLoading = if (it) View.VISIBLE else View.GONE
        val isVisible = if (!it) View.VISIBLE else View.GONE
        viewBinding.progress.visibility = isVisibleLoading
        viewBinding.txtCity.visibility = isVisible
        viewBinding.txtDegree.visibility = isVisible
        viewBinding.txtTime.visibility = isVisible
        viewBinding.imgIcon.visibility = isVisible
    }

    private val successObserver = Observer<CurrentData> { value: CurrentData ->
        Glide.with(requireContext())
            .load(value.condition.icon)
            .placeholder(R.drawable.ic_launcher_background)
            .into(viewBinding.imgIcon)

        viewBinding.apply {
            txtTime.text = value.last_updated
            txtCity.text = value.locationData.name
            txtDegree.text = value.temp_c.toString()
        }
    }

    private val errorObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
}