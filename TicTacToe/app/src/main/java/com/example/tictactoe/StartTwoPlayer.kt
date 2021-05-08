package com.example.tictactoe


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_start_two_player.*
import android.widget.TextView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlin.math.round


class StartTwoPlayer : Fragment(){

    private lateinit var p2BtnMenu : Button
    private lateinit var p2BtnStart: Button
    private lateinit var startPlayer1NameText : TextView
    private lateinit var startPlayer2NameText : TextView
    private lateinit var roundSelector : Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start_two_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        p2BtnMenu = view.findViewById(R.id.twoplayer_btn_menu)
        p2BtnStart = view.findViewById(R.id.twoplayer_btn_start)
        startPlayer1NameText = view.findViewById(R.id.start_player1_name)
        startPlayer2NameText = view.findViewById(R.id.start_player2_name)
        roundSelector = view.findViewById(R.id.start_round_spinner)
        initSpinner()

        p2BtnMenu.setOnClickListener {
            Toast.makeText(activity, "going to menu", Toast.LENGTH_SHORT).show()
            (activity as ActivityStart).setCurrentPage(0)
        }

        p2BtnStart.setOnClickListener {
            val intent = Intent(activity,MainActivity::class.java)
            val player1Name =startPlayer1NameText.text.toString()
            val player2Name =startPlayer2NameText.text.toString()
            val roundSelected = start_round_spinner.selectedItem.toString()
            when {
                startPlayer1NameText.length() >= 6
                -> Toast.makeText(activity,"names cannot exceed 5 characters",Toast.LENGTH_SHORT).show()

                startPlayer2NameText.length() >= 6
                -> Toast.makeText(activity,"names cannot exceed 5 characters",Toast.LENGTH_SHORT).show()

                player1Name == player2Name
                -> Toast.makeText(activity,"names cannot be exactly the same",Toast.LENGTH_SHORT).show()

                player1Name.isEmpty() or player2Name.isEmpty()
                ->Toast.makeText(activity,"you cant leave name-field empty",Toast.LENGTH_SHORT).show()

                else -> {
                    Toast.makeText(activity, "$player1Name goes first", Toast.LENGTH_SHORT).show()
                    intent.putExtra("p1_name", player1Name)
                    intent.putExtra("p2_name", player2Name)
                    intent.putExtra("round", roundSelected)
                    startActivityForResult(intent,1)


                }
            }
        }


    }

    private fun initSpinner(){
        val rounds = arrayOf(1,3,5,10)
        val adapter = ArrayAdapter<Int>(activity, android.R.layout.simple_spinner_item,rounds)
        roundSelector.adapter = adapter
    }


}
