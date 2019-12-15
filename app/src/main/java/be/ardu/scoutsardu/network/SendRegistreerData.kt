package be.ardu.scoutsardu.network

import com.google.gson.annotations.SerializedName


class SendRegistreerData(
    var email: String,
    var voornaam: String,
    var achternaam: String,
    var telNr: String,
    var password: String,
    var passwordConfirmation: String
){
    var type: Int = 0
    var foto: String = "string"
}