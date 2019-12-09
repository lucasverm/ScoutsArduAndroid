package be.ardu.scoutsardu.models

import android.util.Log

class WinkelwagenItem (id: Int, naam:String, prijs:Double, aantal: Int){
    var id: Int = id
    var naam: String = naam
    var prijs: Double = prijs
    var aantal = aantal
    //private lateinit var List<Winkelwagen> Winkelwagens

    fun vermeerderDrank(){
        this.aantal += 1
    }

    fun verminderrDrank(){
        this.aantal -= 1
    }
}