package be.ardu.scoutsardu

import androidx.test.ext.junit.runners.AndroidJUnit4
import be.ardu.scoutsardu.Espresso.AccountWijzigenTest
import be.ardu.scoutsardu.Espresso.LoginTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(AndroidJUnit4::class)
// Runs all unit tests.
@Suite.SuiteClasses(
    LoginTest::class,
    AccountWijzigenTest::class
)
class UnitTestSuite