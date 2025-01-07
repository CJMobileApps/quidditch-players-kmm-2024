package com.cjmobileapps.quidditch_players_kmm_2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

//TODO rename this from MainActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainAndroid()
        }
    }
}

//@Composable
//fun GreetingView(text: String) {
//    Text(text = text)
//}
//
//@Preview
//@Composable
//fun DefaultPreview() {
//    MyApplicationTheme {
//        GreetingView("Hello, Android!")
//    }
//}
