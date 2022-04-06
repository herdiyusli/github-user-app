package com.herdi.yusli.githubappherdiyusli

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.herdi.yusli.githubappherdiyusli.view.MainActivity
import org.junit.Before
import org.junit.Test

class MainActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }


    @Test
    fun tesSettingsButton(){
        onView(withId(R.id.settings_activity)).perform(click())
    }
}