package be.ardu.scoutsardu.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WinkelwagenItemAantal(
    var item: WinkelwagenItem,
    var aantal: Int
):Parcelable{
    var id: Int = 0

    fun vermeerderDrank() {
        this.aantal += 1
    }

    fun verminderrDrank() {
        this.aantal -= 1
    }
}