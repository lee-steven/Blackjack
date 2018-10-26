package com.example.blackjack

class Player {
    var count = 0
    var totalScore = 0
    var arrayList = ArrayList<String>()

    fun getTotal(b: Int): Int{
        totalScore = totalScore + b
        return totalScore
    }

    fun addCard(a: String){
        arrayList.add(a)
    }
}