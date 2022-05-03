package ru.mirea.kryazhin.mireaproject.ui.permissions

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import ru.mirea.kryazhin.mireaproject.R
import ru.mirea.kryazhin.mireaproject.databinding.FragmentHomeBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Sensors.newInstance] factory method to
 * create an instance of this fragment.
 */
class Sensors : Fragment(), SensorEventListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var mActivity: Activity? = null
    private val binding: FragmentHomeBinding? = null

    private var azimuthTextView: TextView? = null
    private var pitchTextView: TextView? = null
    private var rollTextView: TextView? = null
    private var pressureTextView: TextView? = null
    private var temperatureTextView: TextView? = null

    private var sensorManager: SensorManager? = null
    private var accelerometerSensor: Sensor? = null
    private var pressureSensor: Sensor? = null
    private var temperatureSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        sensorManager =
            this.requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        pressureSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PRESSURE)
        temperatureSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensors, container, false)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        azimuthTextView = requireView().findViewById(R.id.textViewAzimuth)
        pitchTextView = requireView().findViewById(R.id.textViewPitch)
        rollTextView = requireView().findViewById(R.id.textViewRoll)
        pressureTextView = requireView().findViewById(R.id.textViewPressure)
        temperatureTextView = requireView().findViewById(R.id.textViewTemperature)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(
            this, accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager?.registerListener(
            this, pressureSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager?.registerListener(
            this, temperatureSensor,
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
        if (event.sensor.type == Sensor.TYPE_PRESSURE) {
            val valuePressure = event.values[0]
            pressureTextView!!.text = "Pressure: $valuePressure"
        }
        if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            val valueTemperature = event.values[0]
            temperatureTextView!!.text = "Temperature: $valueTemperature"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Sensors.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Sensors().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}