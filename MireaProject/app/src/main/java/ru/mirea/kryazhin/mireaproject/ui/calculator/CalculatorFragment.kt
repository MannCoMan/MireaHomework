package ru.mirea.kryazhin.mireaproject.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import net.objecthunter.exp4j.ExpressionBuilder
import ru.mirea.kryazhin.mireaproject.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalculatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalculatorFragment : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false);

        var lastNumaric: Boolean= false
        var stateError: Boolean = false
        var lastDot :Boolean=false

        var outputTextView = view.findViewById(R.id.textView2) as TextView
        var one = view.findViewById(R.id.one) as Button
        var two = view.findViewById(R.id.two) as Button
        var three = view.findViewById(R.id.three) as Button
        var four = view.findViewById(R.id.four) as Button
        var five = view.findViewById(R.id.five) as Button
        var six = view.findViewById(R.id.six) as Button
        var seven = view.findViewById(R.id.seven) as Button
        var eight = view.findViewById(R.id.eight) as Button
        var nine = view.findViewById(R.id.nine) as Button
        var divide = view.findViewById(R.id.divide) as Button
        var multiply = view.findViewById(R.id.multiply) as Button
        var substract = view.findViewById(R.id.subtract) as Button
        var decimal = view.findViewById(R.id.decimal) as Button
        var zero = view.findViewById(R.id.zero) as Button
        var clear = view.findViewById(R.id.clear) as Button
        var add = view.findViewById(R.id.add) as Button
        var equal = view.findViewById(R.id.equal) as Button

        one.setOnClickListener(View.OnClickListener {
            if(stateError)
            {
                outputTextView.text="1"
                stateError=false
            }else {
                outputTextView.append("1")
            }
            lastNumaric=true
        })
        two.setOnClickListener(View.OnClickListener {
            if(stateError)
            {
                outputTextView.text="2"
                stateError=false
            }else {
                outputTextView.append("2")
            }
            lastNumaric=true
        })
        three.setOnClickListener(View.OnClickListener {
            if(stateError)
            {
                outputTextView.text="3"
                stateError=false
            }else {
                outputTextView.append("3")
            }
            lastNumaric=true
        })
        four.setOnClickListener(View.OnClickListener {
            if(stateError)
            {
                outputTextView.text="4"
                stateError=false
            }else {
                outputTextView.append("4")
            }
            lastNumaric=true
        })
        five.setOnClickListener(View.OnClickListener {
            if(stateError)
            {
                outputTextView.text="5"
                stateError=false
            }else {
                outputTextView.append("5")
            }
            lastNumaric=true
        })
        six.setOnClickListener(View.OnClickListener {
            if(stateError)
            {
                outputTextView.text="6"
                stateError=false
            }else {
                outputTextView.append("6")
            }
            lastNumaric=true
        })
        seven.setOnClickListener(View.OnClickListener {
            if(stateError)
            {
                outputTextView.text="7"
                stateError=false
            }else {
                outputTextView.append("7")
            }
            lastNumaric=true
        })
        eight.setOnClickListener(View.OnClickListener {
            if(stateError)
            {
                outputTextView.text="8"
                stateError=false
            }else {
                outputTextView.append("8")
            }
            lastNumaric=true
        })
        nine.setOnClickListener(View.OnClickListener {
            if(stateError)
            {
                outputTextView.text="9"
                stateError=false
            }else {
                outputTextView.append("9")
            }
            lastNumaric=true
        })
        zero.setOnClickListener(View.OnClickListener {
            if(stateError)
            {
                outputTextView.text="0"
                stateError=false
            }else {
                outputTextView.append("0")
            }
            lastNumaric=true
        })
        decimal.setOnClickListener(View.OnClickListener {
            if(lastNumaric && !stateError && !lastDot)
            {
                outputTextView.append(".")
                lastNumaric=false
                lastDot=true
            }
        })

        add.setOnClickListener(View.OnClickListener {
            if(lastNumaric && !stateError)
            {
                outputTextView.append("+")
                lastNumaric=false
                lastDot=false
            }
        })

        divide.setOnClickListener(View.OnClickListener {
            if(lastNumaric && !stateError)
            {
                outputTextView.append("/")
                lastNumaric=false
                lastDot=false
            }
        })

        multiply.setOnClickListener(View.OnClickListener {
            if(lastNumaric && !stateError)
            {
                outputTextView.append("*")
                lastNumaric=false
                lastDot=false
            }
        })

        substract.setOnClickListener(View.OnClickListener {
            if(lastNumaric && !stateError)
            {
                outputTextView.append("-")
                lastNumaric=false
                lastDot=false
            }
        })
        clear.setOnClickListener(View.OnClickListener {
            outputTextView.setText("")
            lastNumaric=false
            stateError=false
            lastDot=false
        })

        equal.setOnClickListener(View.OnClickListener {
            if(lastNumaric && !stateError)
            {
                val text = outputTextView.text.toString()
                val expression= ExpressionBuilder(text).build()
                try
                {
                    val result= expression.evaluate()
                    outputTextView.setText(result.toString())
                    lastDot=true
                }catch (ex:Exception)
                {
                    outputTextView.setText("Error")
                    stateError=true
                    lastNumaric=false
                }
            }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalculatorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalculatorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}