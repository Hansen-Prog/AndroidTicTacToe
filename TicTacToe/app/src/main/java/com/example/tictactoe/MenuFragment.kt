package com.example.tictactoe


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast


class MenuFragment : Fragment() {

    private lateinit var btnTwoPlayer : Button
    private lateinit var btnSinglePlayer : Button
    private lateinit var btnMatchFrag : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnTwoPlayer = view.findViewById(R.id.btn_two_player)
        btnSinglePlayer = view.findViewById(R.id.btn_one_player)
        btnMatchFrag = view.findViewById(R.id.btn_match_frag)

        btnTwoPlayer.setOnClickListener {
            Toast.makeText(activity, "heading over to two player", Toast.LENGTH_SHORT).show()
            (activity as ActivityStart).setCurrentPage(1)
        }
        btnSinglePlayer.setOnClickListener {
            Toast.makeText(activity,"heading over to single player", Toast.LENGTH_SHORT).show()
            (activity as ActivityStart).setCurrentPage(2)
        }
        btnMatchFrag.setOnClickListener {
            val intent = Intent(activity,MatchActivity::class.java)
            Toast.makeText(activity,"heading over to match history", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }


}
