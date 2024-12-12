package com.example.krittaphat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Setting.newInstance] factory method to
 * create an instance of this fragment.
 */
class Setting : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        val usernameTextView = view.findViewById<TextView>(R.id.username)
        val btnChange = view.findViewById<Button>(R.id.changebtn)

        val username = arguments?.getString("USERNAME") ?: "Guest"
        usernameTextView.text = username

        btnChange.setOnClickListener {
            val intent = Intent(requireContext(), UsernameActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}
