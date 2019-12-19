package be.ardu.scoutsardu.network


class SendRegistreerData(
    var email: String,
    var voornaam: String,
    var achternaam: String,
    var password: String,
    var passwordConfirmation: String,
    var telNr:String
){
    var type: Int = 0
    var foto: String = "foto"
}