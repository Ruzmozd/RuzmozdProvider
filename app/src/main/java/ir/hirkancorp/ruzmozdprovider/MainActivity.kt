package ir.hirkancorp.ruzmozdprovider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()

        setContent {
            val navController = rememberNavController()
            ProvideWindowInsets(
                windowInsetsAnimationsEnabled = true,
            ) {
                RuzmozdProviderTheme {
                    Scaffold(

                    ){ padding ->
                        Surface(
                            modifier = Modifier
                                .padding(padding)
                                .imePadding()
                                .fillMaxSize()
                                .statusBarsPadding()
                                .navigationBarsWithImePadding(),
                        ) {

                        }
                    }
                }
            }
        }
    }
}

