package com.example.carexample

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.util.toAndroidPair
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

data class Car(val id: Int, val title: String)

private const val TAG = "test"
val carCategory = arrayListOf(
    Car(id = 0, title = "bmw"),
    Car(id = 1, title = "audi"),
    Car(id = 2, title = "toyota"),
)

val carListWithId = mapOf(
    "redBMW" to 0,
    "blueBMW" to 0,
    "redAudi" to 1,
    "blueAudi" to 1,
    "redToyota" to 2,
    "blueToyota" to 2
)

data class CarDetails(val id: Int, val title: String)

// this returns only one item
val carDetails = arrayListOf(
    CarDetails(id = 0, title = "redBmw"),
    CarDetails(id = 0, title = "blueBmw"),
    CarDetails(id = 1, title = "redAudi"),
    CarDetails(id = 1, title = "blueAudi"),
    CarDetails(id = 2, title = "redToyota"),
    CarDetails(id = 2, title = "blueToyota"),

    )

// Call Navigation in MainActivity with  navController = rememberNavController()
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "category_car") {
        composable("category_car") {
            Category(navController = navController)
        }
        composable(
            "category_car_detail/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                },
            )
        ) { navBackStackEntry ->
            CarDetail(navBackStackEntry.arguments!!.getInt("index"))
        }
    }
}

@Composable
fun Category(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {

        val itemsCat: List<Car> = carCategory
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 60.dp)
                .wrapContentSize(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(Color(0xff24292f))
        ) {
            LazyColumn {
                itemsIndexed(itemsCat) { index, car ->
                    CategoryItemCar(itemCar = car, onClick = {
                        navController.navigate("category_car_detail/${index}")
                    })
                }
            }
        }
    }

}


@Composable
private fun CategoryItemCar(itemCar: Car, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(horizontal = 15.dp)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = itemCar.title,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        )


    }
    Divider(
        color = Color.DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
            .height(1.dp)
    )
}

@Composable
private fun CategoryItemCarDetails(itemCarDetail: CarDetails) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = itemCarDetail.title,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        )


    }
    Divider(
        color = Color.DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
            .height(1.dp)
    )
}

@Composable
fun CarDetail(index: Int) {
    Surface(modifier = Modifier.fillMaxSize()) {

        val indexedCars = carDetails.filter { it.id == index }
        val indexedCarsZip = carCategory.zip(carDetails)
        Log.d(TAG, "Car item idex: $indexedCarsZip")

        Card(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 60.dp)
                .wrapContentSize()
                .clickable { },
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(Color(0xff24292f))
        ) {
            LazyColumn {
                // items does not accept Map type but only accept List,Array,count
                items(indexedCars) { index ->
                    Log.d(TAG,"detail lazyColumn Index: $index")
                    CategoryItemCarDetails(itemCarDetail = index)
                }
            }
        }
    }
}
