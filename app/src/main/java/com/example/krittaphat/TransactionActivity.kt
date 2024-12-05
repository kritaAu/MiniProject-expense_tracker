package com.example.krittaphat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        val btnTrans = findViewById<Button>(R.id.btnTrans)
        val labelinput = findViewById<EditText>(R.id.labelinput)
        val descinput = findViewById<EditText>(R.id.descinput)
        val amountinput = findViewById<EditText>(R.id.amountinput)
        val labellayout = findViewById<TextInputLayout>(R.id.labelLayout)
        val amountlayout = findViewById<TextInputLayout>(R.id.amountLayout)
        val btnclose = findViewById<ImageButton>(R.id.btnClose)

        labelinput.addTextChangedListener {
            if(it!!.count() > 0)
                labellayout.error = null
        }
        amountinput.addTextChangedListener {
            if(it!!.count() > 0)
                amountlayout.error = null
        }
        btnTrans.setOnClickListener {
            val label = labelinput.text.toString()
            val amount = amountinput.text.toString().toDoubleOrNull()
            val description = descinput.text.toString()

            if (label.isEmpty()) {
                labellayout.error = "Please enter a valid label"
            }

            else if (amount == null) {
                amountlayout.error = "Please enter a valid amount"
            }else {
                val transaction = Transaction(0, label, amount,description)
                insert(transaction)
            }
        }
        btnclose.setOnClickListener {
            finish()
        }

    }
    private fun insert(transaction: Transaction){
        val dbHelper: DatabaseHelper
        dbHelper = DatabaseHelper(this)

        GlobalScope.launch {
            dbHelper.insert(transaction)
            finish()
        }
    }
}
