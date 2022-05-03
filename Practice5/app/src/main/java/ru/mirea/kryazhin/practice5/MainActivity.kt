package ru.mirea.kryazhin.practice5

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    private val sensorManager: SensorManager? = null
    private var listCountSensor: ListView? = null
    private var accelerometerSensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listCountSensor = findViewById(R.id.list_view)
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)


        val arrayList = ArrayList<HashMap<String, Any?>>()
        var sensorTypeList: HashMap<String, Any?>
        for (i in sensors.indices) {
            sensorTypeList = HashMap()
            sensorTypeList["Name"] = sensors[i].name
            sensorTypeList["Value"] = sensors[i].maximumRange
            arrayList.add(sensorTypeList)
        }

        val mHistory = SimpleAdapter(
            this, arrayList, android.R.layout.simple_list_item_2, arrayOf("Name", "Value"), intArrayOf(
                android.R.id.text1, android.R.id.text2
            )
        )
        listCountSensor?.setAdapter(mHistory)
        accelerometerSensor = sensorManager
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(
            this, accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val valueAzimuth = event.values[0]
            val valuePitch = event.values[1]
            val valueRoll = event.values[2]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}