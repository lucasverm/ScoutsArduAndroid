package be.ardu.scoutsardu.network

data class WinkelwagenItemProperty (
    //[{"id":1,"naam":"Cola","prijs":1.0,"aantal":0,"winkelwagens":null},{"id":2,"naam":"Bier","prijs":2.0,"aantal":0,"winkelwagens":null},{"id":3,"naam":"Cola","prijs":1.0,"aantal":0,"winkelwagens":null},{"id":4,"naam":"Bier","prijs":2.0,"aantal":0,"winkelwagens":null}]
    val id: Int,
    val naam: String,
    /*cast: @Json(name="eenheidsPrijs") */ val prijs: Double,
    val aantal: Int
)