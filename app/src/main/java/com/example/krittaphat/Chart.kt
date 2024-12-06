package com.example.krittaphat

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class Chart : Fragment() {

    private lateinit var balanceTextView: TextView
    private lateinit var pieChart: PieChart
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_chart, container, false)

        balanceTextView = rootView.findViewById(R.id.balancechart)
        pieChart = rootView.findViewById(R.id.piechart)
        dbHelper = DatabaseHelper(requireContext())

        val balance = getBalanceFromDatabase()

        balanceTextView.text = "฿%.2f".format(balance)

        setupPieChart()
        return rootView
    }

    private fun getBalanceFromDatabase(): Double {
        val transactions = dbHelper.getAll()

        var totalIncome = 0.0
        var totalExpense = 0.0

        for (transaction in transactions) {
            if (transaction.amount >= 0) {
                totalIncome += transaction.amount
            } else {
                totalExpense += Math.abs(transaction.amount)
            }
        }

        return totalIncome - totalExpense
    }

    private fun setupPieChart() {
        val entries = ArrayList<PieEntry>()

        val transactions = dbHelper.getAll()
        var totalIncome = 0.0f
        var totalExpense = 0.0f

        for (transaction in transactions) {
            if (transaction.amount >= 0) {
                totalIncome += transaction.amount.toFloat()
            } else {
                totalExpense += Math.abs(transaction.amount.toFloat())
            }
        }

        entries.add(PieEntry(totalIncome, "Income"))
        entries.add(PieEntry(totalExpense, "Expenses"))

        val dataSet = PieDataSet(entries, "Transactions")
        dataSet.setColors(Color.GREEN, Color.RED)

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate()
    }
}
