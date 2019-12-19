package be.ardu.scoutsardu

import be.ardu.scoutsardu.network.WinkelwagenItem
import be.ardu.scoutsardu.network.WinkelwagenItemAantal
import junit.framework.Assert
import org.junit.Before
import org.junit.Test


class WinkelwagenItemTest {
    private lateinit var winkelwagenItemAantal: WinkelwagenItemAantal
    @Before
    fun before() {
        val item1 = WinkelwagenItem(1, "Fanta", 3.0)
        winkelwagenItemAantal = WinkelwagenItemAantal(item1, 3)
    }

    @Test
    fun winkelwagenItemAantal_vermeerderDrank_drank_vermeerderd_met_1() {
        val aantal = winkelwagenItemAantal.aantal
        winkelwagenItemAantal.vermeerderDrank()
        Assert.assertEquals(winkelwagenItemAantal.aantal, aantal + 1)
    }

    @Test
    fun winkelwagenItemAantal_verminderDrank_drank_verminderd_met_1() {
        val aantal = winkelwagenItemAantal.aantal
        winkelwagenItemAantal.verminderrDrank()
        Assert.assertEquals(winkelwagenItemAantal.aantal, aantal - 1)
    }

    @Test
    fun winkelwagenItemAantal_verminderDrank_drank_gaat_niet_onder_nul() {
        winkelwagenItemAantal.aantal = 0
        winkelwagenItemAantal.verminderrDrank()
        Assert.assertEquals(winkelwagenItemAantal.aantal, 0)
    }

}