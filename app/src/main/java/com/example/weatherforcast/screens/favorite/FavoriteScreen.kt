package com.example.weatherforcast.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforcast.R
import com.example.weatherforcast.model.Favorite
import com.example.weatherforcast.navigation.WeatherScreens
import com.example.weatherforcast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController,
                   favoriteViewModel: FavoriteViewModel = hiltViewModel()) {

    Scaffold(
        topBar = {
            WeatherAppBar(navController = navController,
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,

                ){
                navController.popBackStack()
            }
        }
    ) {
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = favoriteViewModel.favList.collectAsState().value

                LazyColumn(){
                    items(list, key = {it -> it.city}){
                        CityRow(it,navController,favoriteViewModel)
                    }
                }

            }

        }

    }
}

@Composable
fun CityRow(favorite: Favorite, navController: NavController, favoriteViewModel: FavoriteViewModel) {

    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                       navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
            },
        shape = RoundedCornerShape(13.dp),
        color = Color(0xFFB2DFDB)

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(text = favorite.city, modifier = Modifier.padding(start = 4.dp))

            Text(text = favorite.country, modifier = Modifier.padding(start = 4.dp))
            IconButton(onClick = {
                favoriteViewModel.deleteFav(favorite)
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete",
                    tint = Color.Red.copy(alpha = 0.4f) )

            }

            

        }

    }
}


