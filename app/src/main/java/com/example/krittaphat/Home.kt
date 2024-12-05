package com.example.krittaphat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Home : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"
    private lateinit var transactions: ArrayList<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var balanceNum: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        // Initialize transaction data
        transactions = arrayListOf(
            Transaction("Budget", 300.00),
            Transaction("Breakfast", -50.00),
            Transaction("Cookie", -5.00),
            Transaction("Gas", -15.00),
            Transaction("Egg", -10.00),
            Transaction("Steak", -10.00),
            Transaction("Budget", 200.00),
            Transaction("Water", -10.00),
            Transaction("Cola", -99.00),
        )

        // Initialize Adapter and LayoutManager
        transactionAdapter = TransactionAdapter(transactions)
        layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        balanceNum = view.findViewById(R.id.balanceNum)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)

        recyclerView.adapter = transactionAdapter
        recyclerView.layoutManager = layoutManager

        updatedashboard()

        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btnaddTrans)
        btnAdd.setOnClickListener {
            val intent = Intent(requireContext(), TransactionActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun updatedashboard() {
        val totalAmount = transactions.map { it.amount }.sum()
        balanceNum.text = "%.2f".format(totalAmount)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
