package fr.uge.android.flags

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import fr.uge.android.flags.ui.theme.WorldFlagsTheme
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.rememberNavController
import fr.uge.android.flags.ui.theme.Rank
import androidx.compose.runtime.remember as remember


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //CountryDisplayer(country = Japan())
            mainComponent()
            /*val navController = rememberNavController()
            Navigation(navController = navController)*/
            /*val bitmaps = createMapWithLocation(latitude = Japan().geoCoordinates[0], longitude = Japan().geoCoordinates[1])
            MapDisplayer(bitmap = bitmaps.value)*/
            //CountryDisplayer(country = France())
            //FlagsDisplayer(countries = countries())
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun DefaultPreview() {
    WorldFlagsTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun HelloWorld(name: String ="Mouad"){
    var counter by remember { mutableStateOf(0) }
    HelloWorldMessage(name = name, counter = counter)
    WorldMap {
        counter++
    }
}

@Composable
fun HelloWorldMessage(name: String , counter: Int){

    Text( "Hello world $name $counter",
        Modifier.background(Color.Red, RectangleShape),
        color = Color.White,
    )
}
@Composable
fun WorldMap(mapClick: () -> Unit){

    Image(painter = painterResource(id = R.drawable.world_map), contentDescription = "world map",
        Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { mapClick() },
                    onDoubleTap = {
                        repeat(4) {
                            mapClick()
                        }
                    }
                )
            }
    )

}

@Composable
fun MyComposable(){
    var myVal by remember {mutableStateOf(false) }
    Log.d("Recomposition","MyComposable")
    Button(onClick = { myVal = !myVal}) {
        Text(text = "$myVal")
        Log.d("Recomposition","Button Content Lambda")
    }
}

@Composable
fun MadagascarFlag() {
    Row(modifier= Modifier
        .fillMaxSize()
        .border(width = Dp.Hairline, color = Color.Black)) {
        Box(
            Modifier
                .fillMaxHeight()
                .background(color = Color.White)
                .weight(1f / 3f, fill = true)) {  }
        Column(
            Modifier
                .fillMaxHeight()
                .weight(2f / 3f, fill = true)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.Red)
                    .weight(1f / 2f, fill = true)) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.Green)
                    .weight(1f / 2f, fill = true)) {}
        }
    }
}
@Composable
fun CzechFlag() {
    val triangleShape = GenericShape { size, layoutDirection ->
        moveTo(0f, 0f)
        lineTo(size.width, size.height / 2)
        lineTo(0f, size.height)
        lineTo(0f, 0f)
        close()
    }
    Box(modifier= Modifier
        .fillMaxSize()
        .border(width = Dp.Hairline, color = Color.Black)) {
        Column(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f / 2f, fill = true)
                    .background(color = Color.White))
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f / 2f, fill = true)
                    .background(color = Color.Red))
        }
        Row(Modifier.fillMaxHeight(1f)) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .weight(1f / 2f, fill = true)
                    .background(color = Color.Blue, shape = triangleShape))
            Box(
                Modifier
                    .fillMaxHeight()
                    .weight(1f / 2f, fill = true)
                    .background(color = Color.Transparent))
        }
    }
}

