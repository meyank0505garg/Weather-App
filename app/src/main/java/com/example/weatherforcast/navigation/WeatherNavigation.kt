package com.example.weatherforcast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherforcast.screens.favorite.FavoriteScreen
import com.example.weatherforcast.screens.about.AboutScreen
import com.example.weatherforcast.screens.main.MainScreen
import com.example.weatherforcast.screens.main.MainScreenViewModel
import com.example.weatherforcast.screens.search.SearchScreen
import com.example.weatherforcast.screens.settings.SettingsScreen
import com.example.weatherforcast.screens.splash.WeatherSplashScreen


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){

        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)

        }

        val mainScreenRoute = WeatherScreens.MainScreen.name

        composable("${mainScreenRoute}/{city}",
            arguments = listOf(
                navArgument("city"){
                    type = NavType.StringType

                }
            )

            ){navBack ->
            navBack.arguments?.getString("city").let {cityName ->
                val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()

                MainScreen(navController = navController,mainScreenViewModel,cityName = cityName)

            }

//            MainScreen(navController = navController)


        }

        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }


        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.FavoriteScreen.name){
            FavoriteScreen(navController = navController)
        }

        composable(WeatherScreens.SettingScreen.name){
            SettingsScreen(navController = navController)
        }




    }
}