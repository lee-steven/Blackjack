package com.example.blackjack

/**
 * A "Player" class
 */
class Player {
    private var totalScore = 0
    private var arrayList = ArrayList<String>()

    fun getTotal(b: Int): Int{
        totalScore = totalScore + b
        return totalScore
    }

    fun addCard(a: String){
        arrayList.add(a)
    }
}