package tw.teng.hw3

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tw.teng.hw3.ui.MainActivity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("tw.teng.hw3", appContext.packageName)
    }

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    // check recycler view in main activity
    @Test
    fun checkLoginFragment() {
        Espresso.onView(withId(R.id.edit_account)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
    }
}