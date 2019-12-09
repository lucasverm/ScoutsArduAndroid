package be.ardu.scoutsardu.network

data class WinkelwagenItem(
    //[{"id":1,"naam":"Cola","prijs":1.0,"aantal":0,"winkelwagens":null},{"id":2,"naam":"Bier","prijs":2.0,"aantal":0,"winkelwagens":null},{"id":3,"naam":"Cola","prijs":1.0,"aantal":0,"winkelwagens":null},{"id":4,"naam":"Bier","prijs":2.0,"aantal":0,"winkelwagens":null}]
    var id: Int,
    var naam: String,
    /*cast: @Json(name="eenheidsPrijs") */ var prijs: Double,
    var aantal: Int
) {
    fun vermeerderDrank(){
        this.aantal += 1
    }

    fun verminderrDrank(){
        this.aantal -= 1
    }
}