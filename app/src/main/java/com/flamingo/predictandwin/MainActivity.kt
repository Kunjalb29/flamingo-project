package com.flamingo.predictandwin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.flamingo.predictandwin.ui.screen.PredictAndWinScreen
import com.flamingo.predictandwin.ui.theme.PredictAndWinTheme
import com.flamingo.predictandwin.ui.theme.SurfaceDark

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PredictAndWinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SurfaceDark,
                ) {
                    PredictAndWinScreen()
                }
            }
        }
    }
}
