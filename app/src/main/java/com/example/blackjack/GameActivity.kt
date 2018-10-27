package com.example.blackjack

import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.blackjack.R.drawable.back
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent

class GameActivity : AppCompatActivity() {

    private var dealerCards = ArrayList<ImageView>()
    private var playerCards = ArrayList<ImageView>()
    private var player = Player()
    private var dealer = Player()
    private var map = CardMapper()
    lateinit var cardList: ArrayList<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        cardList = map.getIDs(this)
        dealerCards = arrayListOf(dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5)
        playerCards = arrayListOf(playerCard1, playerCard2, playerCard3, playerCard4, playerCard5)
        var dealerFirst = true
        val rand = Random()
        var r = 0
        var id = 0
        var index = 0
        var dealerIndex = 0;
        var dealerFirstCard = 0
        var store = 0;

        //Deal the initial four playerCards to the dealer and player
        //Provided by the AMAZING Taylor Herald
        for (i in 0 until 4) {
            r = rand.nextInt(cardList.size)
            id = cardList[r]
            val name = resources.getResourceEntryName(id)

            if (i % 2 == 0) {
                player.addCard(name)
                val cardValue = findCardValue(name)
                score.text = "Score: " + player.getTotal(cardValue)
                playerCards[index].setImageResource(id)
            }
            else {
                dealer.addCard(name)
                if (dealerFirst) {
                    dealerFirstCard = id
                    dealerFirst = false
                }
                val cardValue = findCardValue(name)
                dealer.getTotal(cardValue)

                if(index == 0){
                    //figuring out how to make first card back.png
                    dealerCards[index].setImageResource(R.drawable.back)
                    store = id;
                }
                else dealerCards[index].setImageResource(id)
                index++

                //MIGHT HAVE TO FIX THIS
                dealerIndex++
            }
            cardList.remove(id)
        }

        //When Player Clicks "PASS"
        passing.setOnClickListener {
            //Dealer "hits" only if total is less than 17
            while(dealer.getTotal(0) < 17) {
                r = rand.nextInt(cardList.size)
                id = cardList[r]
                val name = resources.getResourceEntryName(id)
                cardList.remove(id)
                dealer.addCard(name)
                val cardValue = findCardValue(name)
                dealer.getTotal(cardValue)
                dealerCards[dealerIndex].setImageResource(id)
                dealerIndex++
            }
            dealerCards[0].setImageResource(store)
            declareWinner()
            showEndGameButtons()
        }

        //When Player Clicks "HIT"
        hitting.setOnClickListener {
            r = rand.nextInt(cardList.size)
            id = cardList[r]
            val name = resources.getResourceEntryName(id)
            cardList.remove(r)
            player.addCard(name)
            val cardValue = findCardValue(name)
            score.text = "Score: " + player.getTotal(cardValue)
            playerCards[index].setImageResource(id)
            index++
            if(player.getTotal(0) > 21){
                score.text = "Busted!"
                showEndGameButtons()
            }
        }

        //"NewGame?" Button Clicked
        newGame.setOnClickListener {
            restartGame()
        }
    }

    /**
     * Translates the card name (String a) into an int
     */
    private fun findCardValue(a:String) : Int{
        when(a){
            //Clubs
            "clubs10" -> return 10
            "clubs2" -> return 2
            "clubs3" -> return 3
            "clubs4" -> return 4
            "clubs5" -> return 5
            "clubs6" -> return 6
            "clubs7" -> return 7
            "clubs8" -> return 8
            "clubs9" -> return 9
            "clubs_ace" -> return 1
            "clubs_jack" -> return 10
            "clubs_queen" -> return 10
            "clubs_king" -> return 10
            //Diamonds
            "diamonds10" -> return 10
            "diamonds2" -> return 2
            "diamonds3" -> return 3
            "diamonds4" -> return 4
            "diamonds5" -> return 5
            "diamonds6" -> return 6
            "diamonds7" -> return 7
            "diamonds8" -> return 8
            "diamonds9" -> return 9
            "diamonds_ace" -> return 1
            "diamonds_jack" -> return 10
            "diamonds_queen" -> return 10
            "diamonds_king" -> return 10
            //Hearts
            "hearts10" -> return 10
            "hearts2" -> return 2
            "hearts3" -> return 3
            "hearts4" -> return 4
            "hearts5" -> return 5
            "hearts6" -> return 6
            "hearts7" -> return 7
            "hearts8" -> return 8
            "hearts9" -> return 9
            "hearts_ace" -> return 1
            "hearts_jack" -> return 10
            "hearts_queen" -> return 10
            "hearts_king" -> return 10
            //Spades
            "spades10" -> return 10
            "spades2" -> return 2
            "spades3" -> return 3
            "spades4" -> return 4
            "spades5" -> return 5
            "spades6" -> return 6
            "spades7" -> return 7
            "spades8" -> return 8
            "spades9" -> return 9
            "spades_ace" -> return 1
            "spades_jack" -> return 10
            "spades_queen" -> return 10
            "spades_king" -> return 10
        }
        return 0
    }

    /**
     * Sets score.text to declare the winner of the round
     */
    private fun declareWinner(){
        if(player.getTotal(0) == 21) score.text = "You Win!"
        else if(dealer.getTotal(0) == 21) score.text = "Dealer Wins!"
        else if(player.getTotal(0) < 21 && player.getTotal(0) > dealer.getTotal(0)) score.text = "You Win!"
        else if(dealer.getTotal(0) < 21 && dealer.getTotal(0) > player.getTotal(0)) score.text = "Dealer Wins!"
        else if(dealer.getTotal(0) == 21 && player.getTotal(0) == 21) score.text = "Dealer Wins!"
        else if(player.getTotal(0) == dealer.getTotal(0)) score.text = "Tie!"
        else if(player.getTotal(0) > 21) score.text = "Dealer Wins!"
        else if(dealer.getTotal(0) > 21) score.text = "You Win!"
    }

    /**
     * Restarts the game, new round
     */
    private fun restartGame(){
        finish()
        startActivity(intent)
    }

    /**
     * Hides all buttons but the "New Game?" button
     */
    private fun showEndGameButtons(){
        newGame.visibility = View.VISIBLE
        buttons.visibility = View.INVISIBLE
    }
}
