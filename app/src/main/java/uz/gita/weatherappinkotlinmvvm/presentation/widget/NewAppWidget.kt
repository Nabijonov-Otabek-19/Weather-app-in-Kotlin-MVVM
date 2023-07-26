package uz.gita.weatherappinkotlinmvvm.presentation.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.weatherappinkotlinmvvm.R
import uz.gita.weatherappinkotlinmvvm.domain.repository.WeatherRepositoryImpl
import uz.gita.weatherappinkotlinmvvm.utils.logger

class NewAppWidget : AppWidgetProvider() {

    private val repository = WeatherRepositoryImpl.getInstance()
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        logger("onUpdate")
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, repository, scope)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        logger(intent?.action.toString())
    }
}

@SuppressLint("RemoteViewLayout")
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    repository: WeatherRepositoryImpl,
    scope: CoroutineScope
) {
    logger("updateAppWidget")

    val views = RemoteViews(context.packageName, R.layout.new_app_widget)

    // Get and Set data to Widget
    repository.loadCurrentWeatherByCity("Buxoro").onEach { result ->
        logger("Repository")
        result.onSuccess {
            logger("Result = ${it.condition.text}")
            views.setTextViewText(R.id.appwidget_region, it.locationData.region)
            views.setTextViewText(R.id.appwidget_temp, it.temp_c.toString())
        }
        result.onFailure { logger(it.message ?: "") }
    }.launchIn(scope)

    val intentUpdate = Intent(context, NewAppWidget::class.java)
    intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

    val pendingUpdate = PendingIntent.getBroadcast(
        context, appWidgetId, intentUpdate,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    views.setOnClickPendingIntent(R.id.appwidget_btn, pendingUpdate)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}