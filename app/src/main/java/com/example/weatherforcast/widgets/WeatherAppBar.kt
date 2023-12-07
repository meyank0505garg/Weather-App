package com.example.weatherforcast.widgets

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforcast.model.Favorite
import com.example.weatherforcast.model.Weather
import com.example.weatherforcast.navigation.WeatherScreens
import com.example.weatherforcast.screens.favorite.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun WeatherAppBar(
    title : String = "city ,country",
    icon : ImageVector? = null,
    isMainScreen : Boolean = true,
    elevation: Dp = 0.dp,
    onAddActionClicked : () ->Unit = {} ,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),

    navController: NavController,
    onButtonClicked : () ->Unit = {} ,


) {

    val showIt = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val showDailog = remember{
        mutableStateOf(false)
    }

    if(showDailog.value == true){
        ShowSettingDropDownMenu(showDailog = showDailog,navController = navController)



    }

    CenterAlignedTopAppBar(
        title = {
              Text(text = title,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold,
                fontSize = 15.sp)

             )

        },
        actions = {
                  if(isMainScreen){
                      IconButton(onClick =
                      onAddActionClicked
                      ) {
                          Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                          
                          

                      }

                      IconButton(onClick = { showDailog.value = !showDailog.value }) {
                          Icon(
                              imageVector = Icons.Default.MoreVert,
                              contentDescription = "More Icon"
                          )
                      }
                  }else Box{}
        },
        navigationIcon = {
                         if(icon != null){
//                             Icon(imageVector = icon, contentDescription = null,
//                                 tint = MaterialTheme.colorScheme.onSecondary,
//                                 modifier = Modifier.clickable {
//                                     onButtonClicked.invoke()
//                                 })

                             IconButton(onClick = onButtonClicked) {
                                 Icon(
                                     imageVector = icon,
                                     contentDescription = null,
                                     tint = Color.Black
                                 )
                             }
                         }

            if(isMainScreen){
                val isAlreadyFavList = favoriteViewModel.favList.collectAsState().value
                    .filter {item->
                        (item.city == title.split(",")[0])

                    }

                if(isAlreadyFavList.isNullOrEmpty()){
                    IconButton(onClick = {
                        val datalist = title.split(",")
                        favoriteViewModel.insertFav(Favorite(
                            city = datalist[0],
                            country = datalist[1]
                        )).run {
                            showIt.value = true
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Fav Icon",
                            tint = Color.Red.copy(0.6f),
                            modifier = Modifier.scale(0.9f)

                        )


                    }


                }else{
                    showIt.value = false
                    Box {

                    }

                }

                ShowToast(context = context,showIt)

            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.shadow(2.dp)
            )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {

    if(showIt.value){
        Toast.makeText(context," Added to Favorites",Toast.LENGTH_SHORT).show()
    }

}

@Composable
fun ShowSettingDropDownMenu(showDailog: MutableState<Boolean>, navController: NavController) {

    var expanded by remember{
        mutableStateOf(true)
    }

    val items = listOf("About", "Favorites" , "Settings")
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)) {
        
        DropdownMenu(expanded = expanded , onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(color = Color.White)) {
            
            items.forEachIndexed { index, text ->
                DropdownMenuItem( onClick = {
                    expanded = false
                    showDailog.value =false

                    navController.navigate(

                        when(text){
                            "About" -> WeatherScreens.AboutScreen.name
                            "Favorites" -> WeatherScreens.FavoriteScreen.name
                            else -> WeatherScreens.SettingScreen.name

                        }


                    )




                },
                    text = {

                        Text(text = text,
                            fontWeight = FontWeight(300)
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = when(text){
                                                     "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.Favorite
                            else -> Icons.Default.Settings

                        } , contentDescription = null,
                            tint = Color.LightGray)

                    })
            }
            
        }

    }
}
