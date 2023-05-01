package uz.gita.weatherappinkotlinmvvm.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.weatherappinkotlinmvvm.R
import uz.gita.weatherappinkotlinmvvm.databinding.ScreenDashboardBinding

class DashboardScreen : Fragment(R.layout.screen_dashboard) {
    private val viewBinding by viewBinding(ScreenDashboardBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}