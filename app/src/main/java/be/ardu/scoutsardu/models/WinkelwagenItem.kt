package be.ardu.scoutsardu.models

import android.util.Log

class WinkelwagenItem (id: Int, naam:String, prijs:Double, aantal: Int){
    var Id: Int = id
    var Naam: String = naam
    var Prijs: Double = prijs
    var Aantal = aantal
    //private lateinit var List<Winkelwagen> Winkelwagens

    fun vermeerderDrank(){
        this.Aantal += 1
    }

    fun verminderrDrank(){
        this.Aantal -= 1
    }
}