package com.example.tictactoe


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class StartSinglePlayer : Fragment() {

    private lateinit var p1BtnMenu : Button
    private lateinit var p1BtnStart : Button
    private lateinit var playAsX : Button
    private lateinit var playAsO : Button
    private lateinit var singlePlayerName : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_single_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        p1BtnMenu = view.findViewById(R.id.oneplayer_btn_menu)
        p1BtnStart = view.findViewById(R.id.oneplayer_btn_start)
        playAsX = view.findViewById(R.id.play_x)
        playAsO = view.findViewById(R.id.play_o)
        singlePlayerName = view.findViewById(R.id.start_singlePlayer_name)

        playAsX.setOnClickListener {
            playAsO.isEnabled = false
            playAsO.alpha = 0.3f
        }
        playAsO.setOnClickListener {
            playAsX.isEnabled = false
            playAsX.alpha = 0.3f
        }

        p1BtnMenu.setOnClickListener {
            Toast.makeText(activity,"going to menu",Toast.LENGTH_LONG).show()
            (activity as ActivityStart).setCurrentPage(0)
        }
        p1BtnStart.setOnClickListener {
            val intent = Intent(activity,MainActivity::class.java)
            val playerName = singlePlayerName.text.toString()
            when{
                singlePlayerName.length() >= 6
                ->Toast.makeText(activity,"name cannot exceed 5 characters",Toast.LENGTH_SHORT).show()

                playerName.isEmpty()
                ->Toast.makeText(activity,"you cant leave name-field empty",Toast.LENGTH_SHORT).show()

                playAsX.isEnabled and playAsO.isEnabled
                -> Toast.makeText(activity,"you need to choose x or o",Toast.LENGTH_SHORT).show()

                else ->{
                    if(playAsX.isEnabled){
                        intent.putExtra("x_is_true",true)
                    }
                    else{
                        intent.putExtra("x_is_true",false)
                    }
                    intent.putExtra("single_name", playerName)
                    startActivity(intent)
                }
            }

            }
        }
    }
