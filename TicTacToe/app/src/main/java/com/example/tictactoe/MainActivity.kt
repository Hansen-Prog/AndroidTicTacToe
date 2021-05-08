package com.example.tictactoe

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.tictactoe.Database.MatchModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.round
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent: Intent = intent

        //intent from StartTwoPlayer()
        val player1Name = intent.getStringExtra("p1_name")
        val player2Name = intent.getStringExtra("p2_name")

        //intent from StartOnePlayer()
        val singlePlayer = intent.getStringExtra("single_name")
        val xGoesFirst = intent.getBooleanExtra("x_is_true", false)

        //basically if player chooses X
        if (player1Name.isNullOrBlank() or player2Name.isNullOrBlank() && xGoesFirst) {
            info_player1_name.text = singlePlayer
            info_player2_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33F)
            info_player2_name.text = "TTTBot"
            Toast.makeText(this, "${info_player1_name.text} goes first", Toast.LENGTH_SHORT).show()

        }
        //if player chooses O
        else if (player1Name.isNullOrBlank() or player2Name.isNullOrBlank() && !xGoesFirst) {
            info_player2_name.text = singlePlayer
            info_player1_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33F)
            info_player1_name.text = "TTTBot"
            Toast.makeText(this, "${info_player1_name.text} goes first", Toast.LENGTH_SHORT).show()

        }
        //when the game is two-player
        else {
            info_player1_name.text = player1Name
            info_player2_name.text = player2Name
        }

        btn_menu.setOnClickListener {
            Toast.makeText(this, "Going back to menu", Toast.LENGTH_SHORT).show()
            val btnIntent = Intent(this, ActivityStart::class.java)
            startActivity(btnIntent)
        }

        btn_reset.setOnClickListener {
            clearGame()
            resetScores()
            if (!opponentTurn) {
                Toast.makeText(this, "${info_player1_name.text} goes first", Toast.LENGTH_SHORT).show()
            } else if (opponentTurn) {
                Toast.makeText(this, "${info_player2_name.text} goes first", Toast.LENGTH_SHORT).show()
            }
            info_time.base = SystemClock.elapsedRealtime()
            info_time.start()
        }

        info_time.start()

    }

    //used all-around
    var playerOne = ArrayList<Int>()
    var playerOneScore = 0

    var playerTwo = ArrayList<Int>()
    var playerTwoScore = 0

    //used mostly by game() and whoWon()
    var opponentTurn = false
    var XorO = false
    var tilesPressedTotal = 0
    var roundCounter = 0

    //used by whoWon() and SaveToDB()
    var playerOneWon = false
    var playerTwoWon = false

    //
    @SuppressLint("SetTextI18n")
    fun game(tagView: View) {

        //variable that determines if X or O goes first
        val XorO = tagView as Button
        //Intent from StartSinglePlayer() that determines if bot or player goes first
        val xGoesFirst = intent.extras.getBoolean("x_is_true")

        /** Task 1
         * if xGoesFirst and TTTBot is in game
         */
        if (xGoesFirst && (info_player1_name.text == "TTTBot") or (info_player2_name.text == "TTTBot")) {
            if (!opponentTurn) {
                playerOne.add(tagView.tag.toString().toInt())
                XorO.setBackgroundResource(R.drawable.trump)
                opponentTurn = true
                tilesPressedTotal++
                XorO.isEnabled = false
                AI()
                Toast.makeText(this, "${info_player2_name.text}'s turn", Toast.LENGTH_LONG).show()

            } else {
                playerTwo.add(tagView.tag.toString().toInt())
                XorO.setBackgroundResource(R.drawable.kim)
                opponentTurn = false
                tilesPressedTotal++
                Toast.makeText(this, "${info_player1_name.text}'s turn", Toast.LENGTH_LONG).show()
            }
        }
        /** Task 2
         * if xGoesFirst is false and TTTBot is in game
         */

        else if (!xGoesFirst && (info_player1_name.text == "TTTBot") or (info_player2_name.text == "TTTBot")) {
            if (!opponentTurn) {
                playerOne.add(tagView.tag.toString().toInt())
                XorO.setBackgroundResource(R.drawable.trump)
                opponentTurn = true
                tilesPressedTotal++
                Toast.makeText(this, "${info_player2_name.text}'s turn", Toast.LENGTH_LONG).show()

            } else {
                playerTwo.add(tagView.tag.toString().toInt())
                XorO.setBackgroundResource(R.drawable.kim)
                opponentTurn = false
                tilesPressedTotal++
                Toast.makeText(this, "${info_player1_name.text}'s turn", Toast.LENGTH_LONG).show()
            }
        }
        /** Task 3
         * if non of the above is executed (aka the game is not played against TTTBot)
         */
        else {
            if (!opponentTurn) {
                playerOne.add(tagView.tag.toString().toInt())
                XorO.setBackgroundResource(R.drawable.trump)
                opponentTurn = true
                tilesPressedTotal++
                XorO.isEnabled = false
                Toast.makeText(this, "${info_player2_name.text}'s turn", Toast.LENGTH_LONG).show()

            } else {
                playerTwo.add(tagView.tag.toString().toInt())
                XorO.setBackgroundResource(R.drawable.kim)
                opponentTurn = false
                tilesPressedTotal++
                XorO.isEnabled = false
                Toast.makeText(this, "${info_player1_name.text}'s turn", Toast.LENGTH_LONG).show()
            }
        }
        whoWon()
        /**
         * when the game ends in a draw
         */
        if (tilesPressedTotal == 9) {
            roundCounter++
            if (roundCounter.toString() == intent.getStringExtra("round")) {
                playerTwoWon = false
                playerOneWon = false
                saveToDB()
                gameOver()
            } else {
                clearGame()
                Toast.makeText(this, "Its a DRAW!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Checking player1 and player2 array for winning combinations
    @SuppressLint("SetTextI18n")
    private fun whoWon() {
        //X O O
        //X O O
        //X O O
        if (playerOne.contains(1)
            and
            playerOne.contains(4)
            and
            playerOne.contains(7)
        ) {
            playerOneWon = true
        }
        if (playerTwo.contains(1)
            and
            playerTwo.contains(4)
            and
            playerTwo.contains(7)
        ) {
            playerTwoWon = true
        }
        //O X O
        //O X O
        //O X O
        if (playerOne.contains(2)
            and
            playerOne.contains(5)
            and
            playerOne.contains(8)
        ) {
            playerOneWon = true
        }
        if (playerTwo.contains(2)
            and
            playerTwo.contains(5)
            and
            playerTwo.contains(8)
        ) {
            playerTwoWon = true
        }
        //O O X
        //O O X
        //O O X
        if (playerOne.contains(3)
            and
            playerOne.contains(6)
            and
            playerOne.contains(9)
        ) {
            playerOneWon = true
        }
        if (playerTwo.contains(3)
            and
            playerTwo.contains(6)
            and
            playerTwo.contains(9)
        ) {
            playerTwoWon = true
        }
        //X X X
        //O O O
        //O O O
        if (playerOne.contains(1)
            and
            playerOne.contains(2)
            and
            playerOne.contains(3)
        ) {
            playerOneWon = true
        }
        if (playerTwo.contains(1)
            and
            playerTwo.contains(2)
            and
            playerTwo.contains(3)
        ) {
            playerTwoWon = true
        }
        //O O O
        //X X X
        //O O O
        if (playerOne.contains(4)
            and
            playerOne.contains(5)
            and
            playerOne.contains(6)
        ) {
            playerOneWon = true
        }
        if (playerTwo.contains(4)
            and
            playerTwo.contains(5)
            and
            playerTwo.contains(6)
        ) {
            playerTwoWon = true
        }
        //O O O
        //O O O
        //X X X
        if (playerOne.contains(7)
            and
            playerOne.contains(8)
            and
            playerOne.contains(9)
        ) {
            playerOneWon = true
        }
        if (playerTwo.contains(7)
            and
            playerTwo.contains(8)
            and
            playerTwo.contains(9)
        ) {
            playerTwoWon = true
        }
        //X O O
        //O X O
        //O O X
        if (playerOne.contains(1)
            and
            playerOne.contains(5)
            and
            playerOne.contains(9)
        ) {
            playerOneWon = true
        }
        if (playerTwo.contains(1)
            and
            playerTwo.contains(5)
            and
            playerTwo.contains(9)
        ) {
            playerTwoWon = true
        }
        //O O X
        //O X O
        //X O O
        if (playerOne.contains(3)
            and
            playerOne.contains(5)
            and
            playerOne.contains(7)
        ) {
            playerOneWon = true
        }
        if (playerTwo.contains(3)
            and
            playerTwo.contains(5)
            and
            playerTwo.contains(7)
        ) {
            playerTwoWon = true
        }

        if (playerOneWon or playerTwoWon) {
            if (playerOneWon) {
                roundCounter++
                //if the round counter hits the round counter defined by the user
                if (roundCounter.toString() == intent.getStringExtra("round")) {
                    info_player1_score.text = "${1 + playerOneScore++}"
                    saveToDB()
                    gameOver()
                //else continue a new round
                } else {
                    info_player1_score.text = "${1 + playerOneScore++}"
                    clearGame()
                    Toast.makeText(this, "${info_player1_name.text} won the round", Toast.LENGTH_SHORT).show()
                }

            } else if (playerTwoWon) {
                roundCounter++
                if (roundCounter.toString() == intent.getStringExtra("round")) {
                    info_player2_score.text = "${1 + playerTwoScore++}"
                    saveToDB()
                    gameOver()
                } else {
                    info_player2_score.text = "${1 + playerTwoScore++}"
                    clearGame()
                    Toast.makeText(this, "${info_player2_name.text} won the round", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun saveToDB() {
        //Creates intent of MainActivity() that passes Scores and winners to the MatchActivity().
        // this then continues into  MatchModel() through the MatchActivity(), The Model than carries this info further into the DB
        val matchIntent = Intent(this, MatchActivity::class.java)
        var playerWon = info_player1_name.text.toString()
        var score = "$playerOneScore - $playerTwoScore"
        val isPassed = true
        when {
            info_player1_name.text.isNullOrBlank() -> startActivity(matchIntent)

            /**
             * if playerOne won
             */
            (playerTwoScore < playerOneScore) -> {
                matchIntent.putExtra("PlayerDb", playerWon)
                matchIntent.putExtra("ScoreDb", score)
                matchIntent.putExtra("isPassed", isPassed)
                startActivity(matchIntent)
            }
            /**
             * if playerTwo won
             */
            (playerTwoScore > playerOneScore) -> {
                playerWon = info_player2_name.text.toString()
                score = "$playerOneScore - $playerTwoScore"
                matchIntent.putExtra("PlayerDb", playerWon)
                matchIntent.putExtra("ScoreDb", score)
                matchIntent.putExtra("isPassed", isPassed)
                startActivity(matchIntent)
            }
            /**
             * if draw
             */
            (playerOneScore == playerTwoScore) -> {
                playerWon = "Draw"
                score = "$playerOneScore - $playerTwoScore"
                matchIntent.putExtra("PlayerDb", playerWon)
                matchIntent.putExtra("ScoreDb", score)
                matchIntent.putExtra("isPassed", isPassed)
                startActivity(matchIntent)
            }
        }
    }

    private fun clearGame() {
        playerOne.clear()
        playerTwo.clear()
        opponentTurn = false
        playerOneWon = false
        playerTwoWon = false
        XorO = false
        tilesPressedTotal = 0
        //
        1.until(10).forEach { btnId: Int ->
            val button = findViewById<Button>(resources.getIdentifier("table_tile_$btnId", "id", packageName))
            button.isEnabled = true
            button.setBackgroundResource(android.R.drawable.btn_default)
        }

        info_GameOver.visibility = View.GONE
    }


    private fun disableAllTiles() {
        1.until(10).forEach { btnId: Int ->
            val button = findViewById<Button>(resources.getIdentifier("table_tile_$btnId", "id", packageName))
            button.isEnabled = false
        }

    }

    private fun resetScores() {
        info_player1_score.text = "0"
        playerOneScore = 0
        info_player2_score.text = "0"
        playerTwoScore = 0
    }

    private fun gameOver() {
        clearGame()
        info_GameOver.visibility = View.VISIBLE
        roundCounter = 0
        disableAllTiles()
        info_time.stop()

    }

    private fun AI() {
        val emptyTiles = ArrayList<Int>()
        /**
         * Creates a "empty" list, aka a list with non-clicked buttons
         */
        1.until(10).forEach { unClicked ->
            emptyTiles.add(unClicked)
            if (playerOne.contains(unClicked) or playerTwo.contains(unClicked)) {
                emptyTiles.remove(unClicked)
            }
        }

        /**
         * If there is more than one tile left on the board, click a random tile from "empty" list
         */
        if (tilesPressedTotal < 8) {
            val r = Random
            val randomClick = r.nextInt(emptyTiles.size - 0) + 0
            val button = findViewById<Button>(
                resources.getIdentifier(
                    "table_tile_${emptyTiles[randomClick]}",
                    "id",
                    packageName
                )
            )

            if (1.until(10).contains(emptyTiles[randomClick])) {
                button.performClick()
                button.isEnabled = false
            }
        }
    }
}
