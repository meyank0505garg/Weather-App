package com.example.weatherforcast.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforcast.data.DataOrException
import com.example.weatherforcast.model.Weather
import com.example.weatherforcast.navigation.WeatherScreens
import com.example.weatherforcast.screens.settings.SettingsViewModel
import com.example.weatherforcast.utils.formatDate
import com.example.weatherforcast.utils.formatDecimals
import com.example.weatherforcast.widgets.HumidityWindPressureRow
import com.example.weatherforcast.widgets.SunsetSunrise
import com.example.weatherforcast.widgets.WeatherAppBar
import com.example.weatherforcast.widgets.WeatherDetailRow
import com.example.weatherforcast.widgets.WeatherStateImage


@Composable
fun MainScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    settingsViewModel: SettingsViewModel= hiltViewModel(),
    cityName : String?
               ) {

    val curCity: String = if (cityName!!.isBlank()) "Seattle" else cityName



    val unitFromDb = settingsViewModel.unitList.collectAsState().value

    var unit by remember{
        mutableStateOf("imperial")
    }

    var isImperial by remember {
        mutableStateOf(false)
    }
    Log.d("City", "MainScreen: $curCity ")

    if(unitFromDb.isNotEmpty()){
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
//        unit = "imperial"

        isImperial = unit == "imperial"
        val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(initialValue = DataOrException(loading = true)){
            value = mainScreenViewModel.getWeatherData(curCity,
                units = unit
            )
        }.value

        if(weatherData.loading == true){
            CircularProgressIndicator()

        }else if(weatherData.data != null){

            MainScaffold(weather = weatherData.data!!, navController )

        }
    }







}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {

    Scaffold(
        topBar = {
            WeatherAppBar(weather.city.name + " ," + weather.city.country,
                navController=navController,
                icon = null,
                onAddActionClicked = {
                                     navController.navigate(WeatherScreens.SearchScreen.name)


                },
                onButtonClicked = {


                }
                )

        }


    ) {
       MainContent(data = weather,it)

    }






}



@Composable
fun MainContent(data : Weather,paddingValue : PaddingValues) {

    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"

    Column(modifier = Modifier
        .padding(paddingValue)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formatDate(data.list[0].dt),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)

        )

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)) {

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                
//                Image
                WeatherStateImage(imageUrl = imageUrl)
                
//                Text
                Text(text = formatDecimals(data.list[0].temp.day) + "°", style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Text(text = data.list[0].weather[0].main  , fontStyle = FontStyle.Italic)

            }

        }

        HumidityWindPressureRow(weather = data.list[0] )

        Divider()

        SunsetSunrise(weather = data.list[0])

        Text(text = "This Week" ,
            style =MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
            )

        Surface(modifier = Modifier.fillMaxSize(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(14.dp)
            

            ) {
            
            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
                ){

                items(data.list){item ->

                    WeatherDetailRow(item)



                }

            }

        }


        



    }



}
