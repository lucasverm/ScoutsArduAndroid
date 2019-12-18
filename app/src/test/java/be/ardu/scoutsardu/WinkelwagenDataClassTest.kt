package be.ardu.scoutsardu

import be.ardu.scoutsardu.database.WinkelwagenDatabaseClass
import be.ardu.scoutsardu.network.Gebruiker
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.network.WinkelwagenItem
import be.ardu.scoutsardu.network.WinkelwagenItemAantal
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WinkelwagenDataClassTest {

    lateinit var testWinkelwagen: Winkelwagen
    lateinit var winkelwagenItemsAantal: ArrayList<WinkelwagenItemAantal>
    lateinit var gebruiker: Gebruiker
    lateinit var testWinkelwagenDatabaseClass: WinkelwagenDatabaseClass

    @Before
    fun before() {
        gebruiker = Gebruiker(
            "TestUserVoornaam",
            "TestUserAchternaam",
            "TestUser@email.com",
            "0412345678",
            ArrayList()
        )
        winkelwagenItemsAantal = ArrayList()
        var item1 = WinkelwagenItem(1, "Fanta", 3.0)
        var itemAantal1 = WinkelwagenItemAantal(item1, 3)
        winkelwagenItemsAantal.add(itemAantal1)
        var item2 = WinkelwagenItem(2, "Cola", 2.0)
        var itemAantal2 = WinkelwagenItemAantal(item2, 2)
        winkelwagenItemsAantal.add(itemAantal2)
        testWinkelwagenDatabaseClass = WinkelwagenDatabaseClass(
            18,
            12,
            2019,
            12,
            56,
            true,
            "testGebruikerVoornaam",
            "testGebruikerAchternaam",
            Gson().toJson(winkelwagenItemsAantal),
            "mijnHistoriek"
        )
        testWinkelwagen = testWinkelwagenDatabaseClass.toPropertyObject()
    }

    fun database_winkelwagen_naar_property_geeft_betaald_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.betaald, testWinkelwagen.betaald)
    }

    @Test
    fun database_winkelwagen_naar_property_geeft_datumDag_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.datumDag, testWinkelwagen.datumDag)
    }

    @Test
    fun database_winkelwagen_naar_property_geeft_datumMaand_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.datumMaand, testWinkelwagen.datumMaand)
    }

    @Test
    fun database_winkelwagen_naar_property_geeft_datumJaar_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.datumJaar, testWinkelwagen.datumJaar)
    }

    @Test
    fun database_winkelwagen_naar_property_geeft_datumUur_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.datumUur, testWinkelwagen.datumUur)
    }

    @Test
    fun database_winkelwagen_naar_property_geeft_datumMinuten_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.datumMinuten, testWinkelwagen.datumMinuten)
    }

    @Test
    fun database_winkelwagen_naar_property_geeft_gebruikerVoornaam_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.gebruikerVoornaam, testWinkelwagen.gebruiker.voornaam)
    }

    @Test
    fun database_winkelwagen_naar_property_geeft_gebruikerAchternaam_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.gebruikerAchernaam, testWinkelwagen.gebruiker.achternaam)
    }

    @Test
    fun database_winkelwagen_naar_property_geeft_data_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.data, Gson().toJson(testWinkelwagen.winkelwagenItems))
    }

}