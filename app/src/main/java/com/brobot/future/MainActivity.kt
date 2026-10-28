package com.brobot.future

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.brobot.future.domain.BrobotViewModel
import com.brobot.future.ui.BrobotApp
import com.brobot.future.ui.theme.BrobotColors
import com.brobot.future.ui.theme.BrobotTheme

class MainActivity : ComponentActivity() {

    private val viewModel: BrobotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BrobotTheme(
                largeText = viewModel.largeText,
                reduceMotion = viewModel.reduceMotion
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BrobotColors.Bg
                ) {
                    BrobotApp(viewModel)
                }
            }
        }
    }
}
