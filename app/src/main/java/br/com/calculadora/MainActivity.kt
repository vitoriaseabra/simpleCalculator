package br.com.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.calculadora.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val numbers = setOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    private var pointCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //Numbers
        binding.numberZero.setOnClickListener { addExpression("0", true) }
        binding.numberOne.setOnClickListener { addExpression("1", true) }
        binding.numberTwo.setOnClickListener { addExpression("2", true) }
        binding.numberThree.setOnClickListener { addExpression("3", true) }
        binding.numberFour.setOnClickListener { addExpression("4", true) }
        binding.numberFive.setOnClickListener { addExpression("5", true) }
        binding.numberSix.setOnClickListener { addExpression("6", true) }
        binding.numberSeven.setOnClickListener { addExpression("7", true) }
        binding.numberEight.setOnClickListener { addExpression("8", true) }
        binding.numberNine.setOnClickListener { addExpression("9", true) }

        binding.point.setOnClickListener {
            if (pointCount == 0) {
                pointCount++
                addExpression(".", true)
            }
        }

        //Operators
        binding.sum.setOnClickListener {
            addOperator("+")
        }

        binding.subtraction.setOnClickListener {
            addOperator("-")
        }

        binding.multiplication.setOnClickListener {
            addOperator("*")
        }

        binding.division.setOnClickListener {
            addOperator("/")
        }

        //Buttons
        binding.clear.setOnClickListener {
            binding.expression.text = ""
            binding.result.text = ""
            pointCount = 0
        }

        binding.backspace.setOnClickListener {
            val string = binding.expression.text.toString()

            if(string.isNotBlank()) {
                binding.expression.text = string.substring(0,string.length-1)
            }
            binding.result.text = ""
        }

        binding.equals.setOnClickListener {
            try {
                val expression = ExpressionBuilder(binding.expression.text.toString()).build()

                val result = expression.evaluate()
                val longResult = result.toLong()

                if(result == longResult.toDouble()) {
                    binding.result.text = longResult.toString()
                } else {
                    binding.result.text = result.toString()
                }
            } catch (e: Exception) {
                Log.e(this.javaClass.simpleName, "onCreate: Error in the expression")
            }
        }
    }

    private fun addOperator(operator: String) {
        if (binding.expression.text.last() in numbers) {
            pointCount = 0
            addExpression(operator, false)
        }
    }

    private fun addExpression(expression: String, clearData: Boolean) {

        if(binding.result.text.isNotEmpty()) {
            binding.expression.text = ""
        }

        if(clearData) {
            binding.result.text = ""
            binding.expression.append(expression)
        } else {
            binding.expression.append(binding.result.text)
            binding.expression.append(expression)
            binding.result.text = ""
        }
    }
}