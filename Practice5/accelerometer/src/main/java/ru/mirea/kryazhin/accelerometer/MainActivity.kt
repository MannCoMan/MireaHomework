package ru.mirea.kryazhin.accelerometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    private var azimuthTextView: TextView? = null
    private var pitchTextView: TextView? = null
    private var rollTextView: TextView? = null
    private var sensorManager: SensorManager? = null
    private var accelerometerSensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        azimuthTextView = findViewById(R.id.textViewAzimuth)
        pitchTextView = findViewById(R.id.textViewPitch)
        rollTextView = findViewById(R.id.textViewRoll)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(
            this, accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val valueAzimuth = event.values[0]
            val valuePitch = event.values[1]
            val valueRoll = event.values[2]
            azimuthTextView!!.text = "Azimuth: $valueAzimuth"
            pitchTextView!!.text = "Pitch: $valuePitch"
            rollTextView!!.text = "Roll: $valueRoll"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}