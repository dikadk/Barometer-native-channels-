package com.coolcodezone.barometer_plugin

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** BarometerPlugin */
class BarometerPlugin : FlutterPlugin, MethodCallHandler, SensorEventListener2 {

    private lateinit var sensorManager: SensorManager
    private var pressure: Sensor? = null
    private var latestReading = 25.0f
    private var applicationContext : Context? = null

    private lateinit var channel: MethodChannel


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.flutterEngine.dartExecutor, "barometer_plugin")
        channel.setMethodCallHandler(this)
        applicationContext = flutterPluginBinding.applicationContext
    }

    companion object {

        @JvmStatic
        fun registerWith(registrar: Registrar) {
            Log.e("BAROMETER","Initialize")
            val channel = MethodChannel(registrar.messenger(), "barometer_plugin")
            channel.setMethodCallHandler(BarometerPlugin())
        }
    }

    private fun initialiseBarometer(): Boolean {
        sensorManager = applicationContext?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        Log.e("BAROMETER","Initialized BAROMETER")
        return sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun getBarometer(): Float {
        return latestReading
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "getBarometer" -> {
                result.success(getBarometer())
                return
            }
            "initialiseBarometer" -> {
                result.success(initialiseBarometer())
                return
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onFlushCompleted(sensor: Sensor?) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        Log.e("BAROMETER","Value changed ${event?.values?.get(0)}")
        latestReading = event?.values?.get(0) ?: 0.0f

    }
}
