package com.example.group_7b_calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var resultTV: TextView
    private lateinit var workingsTV: TextView

    private var stateError: Boolean = false
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTV = findViewById(R.id.resultTV)
        workingsTV = findViewById(R.id.workingsTV)

        // Numbers
        findViewById<Button>(R.id.btn0).setOnClickListener { onDigit("0") }
        findViewById<Button>(R.id.btn1).setOnClickListener { onDigit("1") }
        findViewById<Button>(R.id.btn2).setOnClickListener { onDigit("2") }
        findViewById<Button>(R.id.btn3).setOnClickListener { onDigit("3") }
        findViewById<Button>(R.id.btn4).setOnClickListener { onDigit("4") }
        findViewById<Button>(R.id.btn5).setOnClickListener { onDigit("5") }
        findViewById<Button>(R.id.btn6).setOnClickListener { onDigit("6") }
        findViewById<Button>(R.id.btn7).setOnClickListener { onDigit("7") }
        findViewById<Button>(R.id.btn8).setOnClickListener { onDigit("8") }
        findViewById<Button>(R.id.btn9).setOnClickListener { onDigit("9") }

        // Operators
        findViewById<Button>(R.id.btnPlus).setOnClickListener { onOperator("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { onOperator("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperator("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperator("/") }

        // Brackets
        findViewById<Button>(R.id.btnLeftBracket).setOnClickListener { onBracket("(") }
        findViewById<Button>(R.id.btnRightBracket).setOnClickListener { onBracket(")") }

        // Decimal Point
        findViewById<Button>(R.id.btnDot).setOnClickListener { onDecimalPoint() }

        // Clear Button
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClear() }

        // Delete Button
        findViewById<Button>(R.id.btnDelete).setOnClickListener { onDelete() }

        // Equals Button
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqual() }
    }

    private fun onDigit(digit: String) {
        if (stateError) {
            workingsTV.text = digit
            stateError = false
        } else {
            workingsTV.append(digit)
        }
        lastNumeric = true
    }

    private fun onOperator(operator: String) {
        if (lastNumeric && !stateError) {
            workingsTV.append(operator)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun onBracket(bracket: String) {
        if (stateError) {
            workingsTV.text = bracket
            stateError = false
        } else {
            workingsTV.append(bracket)
        }
        lastNumeric = bracket == ")"
        lastDot = false
    }

    private fun onDecimalPoint() {
        if (lastNumeric && !stateError && !lastDot) {
            workingsTV.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onClear() {
        workingsTV.text = ""
        resultTV.text = ""
        stateError = false
        lastNumeric = false
        lastDot = false
    }

    @SuppressLint("SetTextI18n")
    private fun onDelete() {
        val length = workingsTV.text.length
        if (length > 0) {
            val newText = workingsTV.text.substring(0, length - 1)
            workingsTV.text = newText
            // Update lastNumeric and lastDot based on the remaining text
            lastNumeric = newText.isNotEmpty() && newText.last().isDigit()
            lastDot = newText.contains(".")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val expressionText = workingsTV.text.toString()
            try {
                val expression = ExpressionBuilder(expressionText).build()
                val result = expression.evaluate()
                resultTV.text = result.toString()
                lastDot = true
            } catch (ex: Exception) {
                resultTV.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}
