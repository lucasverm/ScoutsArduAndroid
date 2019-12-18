package be.ardu.scoutsardu

import be.ardu.scoutsardu.database.WinkelwagenDatabaseClass
import be.ardu.scoutsardu.network.Gebruiker
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.network.WinkelwagenItem
import be.ardu.scoutsardu.network.WinkelwagenItemAantal
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WinkelwagenTest {

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
        testWinkelwagen =
            Winkelwagen(
                1,
                18,
                12,
                2019,
                12,
                56,
                winkelwagenItemsAantal,
                true,
                gebruiker
            )
        testWinkelwagenDatabaseClass = testWinkelwagen.toDatabaseObject("mijnHistoriek")
    }


    @Test
    fun addAdditionalZero_voegt_een_nul_toe_als_string_uit_1_cijfer_bestaat() {
        assertEquals(testWinkelwagen.addAdditionalZero("1"), "01")
    }

    @Test
    fun addAdditionalZero_voegt_geen_nul_toe_als_string_uit_2_cijfer_bestaat() {
        assertEquals(testWinkelwagen.addAdditionalZero("12"), "12")
    }

    @Test
    fun berekenTotaal_is_juist() {
        assertEquals(testWinkelwagen.getTotaal(), 13.0)
    }

    @Test
    fun winkelwagen_geeft_datum_juist_terug() {
        assertEquals(testWinkelwagen.getDatum(), "18/12/2019")
    }

    @Test
    fun winkelwagen_geeft_tijd_juist_terug() {
        assertEquals(testWinkelwagen.getTijd(), "12:56")
    }

    @Test
    fun winkelwagen_naar_databse_object_geeft_betaald_juist_weer (){
      
        assertEquals(testWinkelwagenDatabaseClass.betaald, testWinkelwagen.betaald)
    }

    @Test
    fun winkelwagen_naar_databse_object_geeft_datumDag_juist_weer (){
      
        assertEquals(testWinkelwagenDatabaseClass.datumDag, testWinkelwagen.datumDag)
    }

    @Test
    fun winkelwagen_naar_databse_object_geeft_datumMaand_juist_weer (){
      
        assertEquals(testWinkelwagenDatabaseClass.datumMaand, testWinkelwagen.datumMaand)
    }

    @Test
    fun winkelwagen_naar_databse_object_geeft_datumJaar_juist_weer (){
      
        assertEquals(testWinkelwagenDatabaseClass.datumJaar, testWinkelwagen.datumJaar)
    }

    @Test
    fun winkelwagen_naar_databse_object_geeft_datumUur_juist_weer (){
      
        assertEquals(testWinkelwagenDatabaseClass.datumUur, testWinkelwagen.datumUur)
    }

    @Test
    fun winkelwagen_naar_databse_object_geeft_datumMinuten_juist_weer (){
      
        assertEquals(testWinkelwagenDatabaseClass.datumMinuten, testWinkelwagen.datumMinuten)
    }

    @Test
    fun winkelwagen_naar_databse_object_geeft_gebruikerVoornaam_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.gebruikerVoornaam, testWinkelwagen.gebruiker.voornaam)
    }

    @Test
    fun winkelwagen_naar_databse_object_geeft_gebruikerAchternaam_juist_weer (){

        assertEquals(testWinkelwagenDatabaseClass.gebruikerAchernaam, testWinkelwagen.gebruiker.achternaam)
    }
}