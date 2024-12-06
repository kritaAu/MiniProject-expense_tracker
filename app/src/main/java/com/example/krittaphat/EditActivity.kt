package com.example.krittaphat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class EditActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        val label = intent.getStringExtra("label")
        val amount = intent.getDoubleExtra("amount", 0.0)
        val description = intent.getStringExtra("description")
        val transactionId = intent.getIntExtra("id", -1)

        dbHelper = DatabaseHelper(this)

        val labelUpdate: TextInputEditText = findViewById(R.id.labelUpdate)
        val amountUpdate: TextInputEditText = findViewById(R.id.amountUpdate)
        val descUpdate: TextInputEditText = findViewById(R.id.descUpdate)

        labelUpdate.setText(label)
        amountUpdate.setText(amount.toString())
        descUpdate.setText(description)

        val btnUpdate: Button = findViewById(R.id.btnUpdate)
        // แสดงข้อมูลใน EditText
        labelUpdate.setText(label)
        amountUpdate.setText(amount.toString())
        descUpdate.setText(description)

        val btnClose2 = findViewById<ImageButton>(R.id.btnClose2)
        btnClose2.setOnClickListener {
            finish()
        }

        btnUpdate.setOnClickListener {
            val updatedLabel = labelUpdate.text.toString()
            val updatedAmount = amountUpdate.text.toString().toDouble()
            val updatedDescription = descUpdate.text.toString()
            val transactionId = intent.getIntExtra("id", -1)
            Log.d("EditActivity", "Transaction ID: $transactionId")

            if (transactionId == -1) {
                Toast.makeText(this, "Invalid transaction ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val updateSuccess = dbHelper.update(transactionId, updatedLabel, updatedAmount, updatedDescription)

            if (updateSuccess>0) {
                val resultIntent = Intent()
                resultIntent.putExtra("label", updatedLabel)
                resultIntent.putExtra("amount", updatedAmount)
                resultIntent.putExtra("description", updatedDescription)
                setResult(RESULT_OK, resultIntent)
                finish()
            }else {
                // ถ้าไม่สามารถอัปเดตได้ แสดงข้อความแจ้งเตือน
                Toast.makeText(this, "Failed to update transaction", Toast.LENGTH_SHORT).show()
            }
        }

    }

}