package com.example.krittaphat

import android.content.Intent
import android.widget.TextView
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.reflect.Type

class DetailActivity : AppCompatActivity() {
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val updatedLabel = data?.getStringExtra("label")
            val updatedAmount = data?.getDoubleExtra("amount", 0.0)
            val updatedDescription = data?.getStringExtra("description")
            val typeTextView: TextView = findViewById(R.id.TypeTextView)


            // อัปเดต UI
            val labelTextView: TextView = findViewById(R.id.labelTextView)
            val amountTextView: TextView = findViewById(R.id.amountTextView)
            val descTextView: TextView = findViewById(R.id.descTextView)

            labelTextView.text = updatedLabel ?: "No Label"
            amountTextView.text = "฿%.2f".format(updatedAmount)
            descTextView.text = updatedDescription ?: "No Description"
            if (updatedAmount != null) {
                if (updatedAmount < 0) {
                    typeTextView.text = "expenses"
                } else {
                    typeTextView.text = "income"
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val labelTextView: TextView = findViewById(R.id.labelTextView)
        val amountTextView: TextView = findViewById(R.id.amountTextView)
        val descTextView: TextView = findViewById(R.id.descTextView)
        val btnclose1: ImageButton = findViewById(R.id.btnClose1)


        val label = intent.getStringExtra("label") ?: "No Label"
        val amount = intent.getDoubleExtra("amount", 0.0)
        val description = intent.getStringExtra("description")
        val transactionId = intent.getIntExtra("id", -1)
        val Type: TextView = findViewById(R.id.TypeTextView)

            if(amount<0)Type.text="expenses"
            else Type.text="income"
            labelTextView.text = "$label"
            amountTextView.text = "฿%.2f".format(amount)
            descTextView.text = "$description"


        btnclose1.setOnClickListener {
            finish()
        }

        val editBtn: Button = findViewById(R.id.editBtn)
        editBtn.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)

            intent.putExtra("label", label)
            intent.putExtra("amount", amount)
            intent.putExtra("description", description)
            intent.putExtra("id", transactionId)

            resultLauncher.launch(intent)
        }

        val delBtn: Button = findViewById(R.id.delBtn)
        delBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this transaction?")
                .setNegativeButton("Yes") { dialog, id ->
                    val transactionId = intent.getIntExtra("id", -1)
                    if (transactionId != -1) {
                        val dbHelper = DatabaseHelper(this)
                        dbHelper.delete(transactionId)
                        Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Transaction not found", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
                .setPositiveButton ("No") { dialog, id ->

                    dialog.dismiss()
                }

            builder.create().show()
        }
    }
}
