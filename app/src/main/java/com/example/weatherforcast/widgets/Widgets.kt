package com.example.weatherforcast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherforcast.R
import com.example.weatherforcast.model.WeatherItem
import com.example.weatherforcast.utils.formatDate
import com.example.weatherforcast.utils.formatDateTime
import com.example.weatherforcast.utils.formatDecimals


@Composable
fun WeatherDetailRow(item: WeatherItem) {

    val imageUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png"

    Surface(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(3.dp),
        color = Color.White

    ) {

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {

            Text(text = formatDate(item.dt).split(",")[0],
                modifier = Modifier.padding(start = 5.dp))

            WeatherStateImage(imageUrl = imageUrl)

            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(text = item.weather[0].description ,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium)

            }

            Text(text = formatDecimals(item.temp.max) + "°",
                color = Color.Blue.copy(alpha = 0.7f))

            Text(text = formatDecimals(item.temp.min) + "°",
                color = Color.LightGray)

        }


    }

}

@Composable
fun SunsetSunrise(weather: WeatherItem) {

    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){

        Row(modifier = Modifier.padding(4.dp) ,
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.sunrise), contentDescription = "Sunrise Icon",
                modifier = Modifier.size(20.dp))

            Text(text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.labelMedium)

        }


        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Icon(painter = painterResource(id = R.drawable.sunset), contentDescription = "Sunset Icon",
                modifier = Modifier.size(20.dp))

            Text(text =  formatDateTime(weather.sunset),
                style = MaterialTheme.typography.labelMedium)


        }
    }


}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {

        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Icon(painter = painterResource(id = R.drawable.humidity), contentDescription = "Humidity Icon",
                modifier = Modifier.size(20.dp))

            Text(text = "${weather.humidity} %",
                style = MaterialTheme.typography.labelMedium)


        }



        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp))

            Text(text = "${weather.pressure} psi",
                style = MaterialTheme.typography.labelMedium)


        }

        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp))

            Text(text = "${weather.speed} mp",
                style = MaterialTheme.typography.labelMedium)


        }

    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {

    Image(painter = rememberAsyncImagePainter(imageUrl), contentDescription = "Icon image",
        modifier = Modifier.size(80.dp))

}
