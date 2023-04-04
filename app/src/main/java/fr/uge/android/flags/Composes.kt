package fr.uge.android.flags

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.android.flags.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.getInstance


class Flags {

    //German Flag
    @Composable
    @Preview
    fun GermanyFlag(){
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .background(color = Color.Black))
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = gerRed))
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = gerGold))
        }
    }

    //France Flag
    @Composable
    fun FranceFlag(){
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .weight(1f)
                .background(color = frBlue)
                .fillMaxHeight())
            Box(modifier = Modifier
                .weight(1f)
                .background(color = Color.White)
                .fillMaxHeight())
            Box(modifier = Modifier
                .weight(1f)
                .background(color = frRed)
                .fillMaxHeight())
        }
    }

    //Japan Flag
    @Composable
    fun JapanFlag(){
        Box(modifier = Modifier
            .background(Color.White)
            .fillMaxSize()){
            Box(modifier = Modifier.fillMaxSize(0.3f)
                .align(Alignment.Center)
                .background(Color.Red)
                .clip(CircleShape)
            )
        }
    }

    //Qatar Flag
    @Composable
    @Preview
    fun QatarFlag(){
        Row(modifier = Modifier
            .background(color = Maroon)
            .fillMaxSize()) {
            Box(modifier = Modifier
                .background(Color.White)
                .fillMaxHeight()
                .weight(1f))
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)) {
                repeat(9){
                    Column(Modifier.weight(1f)) {
                        QatTriangle()
                    }
                }
            }
            Box(modifier = Modifier
                .background(Maroon)
                .fillMaxHeight()
                .weight(2f))
        }
    }
    
    @Composable
    fun QatTriangle(){
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(TriangleShape)
            .background(color = Color.White))
    }
    protected val TriangleShape = GenericShape { size, _ ->
        // 1)
        moveTo(0f , 0f)
        // 2)
        lineTo(size.width/2f, size.height/2f)
        // 3)
        lineTo(0f, size.height)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClockDisplayer(tz: TimeZone){

    val formatter = SimpleDateFormat("MMM d, yyyy h:mm:ss a")
    formatter.setTimeZone(tz)
    var currentTime by remember {
        mutableStateOf(formatter.format(getInstance(tz).time))
    }
    LaunchedEffect(currentTime) {
        while(true) {
            delay(1000)
            try {
                currentTime = formatter.format(getInstance(tz).time)
            }catch (e: Exception){}
        }
    }
    Text(text = currentTime,fontSize = 20.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
}

@Composable
fun RankedValueDisplayer(value: Float, unit: String, rank: Rank){
    Box(modifier = Modifier
        .height(IntrinsicSize.Min)
        .background(color = Color.White)
        .border(2.dp, color = Color.Black)) {
        Box(modifier = Modifier
            .background(color = Color.Yellow)
            .fillMaxWidth(rank.ratio)
            .fillMaxHeight())
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "$value.toInt() $unit", textAlign = TextAlign.Start, modifier = Modifier.padding(3.dp))
            Text(text = "${rank.position} / ${rank.maxPosition}", modifier = Modifier
                .padding(3.dp)
                .align(Alignment.CenterEnd))
        }
    }
}

@Composable
fun labels(){
    Column() {
        Text(text = "Area")
        Text(text = "Population")
        Text(text = "Density")
        Text(text = "GDP per capita")
    }
}

@Composable
fun ComputeWidth(labels: @Composable () -> Unit, content: @Composable (width: Dp) -> Unit) {
    SubcomposeLayout { constraints ->
        // on mesure la longueur des labels (on peut passer un Row { Text(...) Text(...) ... })
        val width = subcompose("labels") {
            labels()
        }[0].measure(Constraints()).width.toDp()
        // on réalise la mesure du contenu que l'on veut rendre en indiquant la longueur des labels
        val contentPlaceable = subcompose("content") { content(width) }[0].measure(constraints)
        // on réalise le rendu final en placant le contenu à son emplacement par défaut en haut à gauche
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CountryDisplayer(country: Country){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        Text(text = country.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.095f), textAlign = TextAlign.Center)
        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f)
                .padding(bottom = 40.dp)) {
                country.getFlag{
                    flag(name = country.name)
                }
                 }
            Image(
                painter =
                painterResource(id = country.resourceId),
                contentDescription = "${country.name}",
                modifier = Modifier.fillMaxSize(0.5f))
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.0976f)) {
            ClockDisplayer(tz = country.tz)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .padding(top = 30.dp)){
            ComputeWidth(labels = { labels() }){
                    width: Dp ->
                Column() {
                    for (i in 1..country.facts.size){
                        Row() {
                            Text(text = "${country.facts[i-1].name}", modifier = Modifier
                                .width(width)
                                .align(Alignment.CenterVertically))
                            Box(modifier = Modifier.padding(4.dp)){RankedValueDisplayer(value = country.facts[i-1].value, unit = country.facts[i-1].unit, rank = country.facts[i-1].rank)}
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)){
            MapDisplayer(createMapWithLocation(latitude = country.geoCoordinates[0], longitude = country.geoCoordinates[1]).value)
        }
    }
}


@Composable
fun FlagDisplayer(country: Country){

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        Box(){
            country.getFlag {
                when (country.name) {
                    "France" -> Flags().FranceFlag()
                    "Japan" -> Flags().JapanFlag()
                    "Qatar" -> Flags().QatarFlag()
                    "Germany" -> Flags().GermanyFlag()
                }
            }
            Text(
                text = "${country.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun FlagsDisplayer(countries: List<Country>){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    LazyColumn (modifier = Modifier
        .background(Color.LightGray)
        .padding(10.dp, bottom = 40.dp, end = 10.dp, top = 10.dp)
        , verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        items(countries){
            country -> Box(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .width(screenWidth)
            .height(screenHeight / 2)
            .background(Color.Transparent)) {
                FlagDisplayer(country = country)
            }
        }
    }
}

@Composable
fun createMapWithLocation(latitude: Float, longitude: Float): State<ImageBitmap?> {
    val context = LocalContext.current
    return produceState<ImageBitmap?>(initialValue = null, latitude, longitude) {
        val mapBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.world_map, BitmapFactory.Options().also { it.inMutable = true })
        val canvas = Canvas(mapBitmap)
        val radius = minOf(canvas.width, canvas.height) / 50f
        val x = (longitude + 180f) / 360f * canvas.width
        val y = (1f - (latitude + 90f) / 180f) * canvas.height
        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = android.graphics.Color.BLUE
        canvas.drawCircle(x, y, radius, paint)
        value = mapBitmap.asImageBitmap()
    }
}

@Composable
fun createMapWithLocation2(latitude: Float, longitude: Float): State<ImageBitmap?> {
    val context = LocalContext.current
    return produceState<ImageBitmap?>(initialValue = null, latitude, longitude) {
        val originalImageBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.world_map).asImageBitmap()
        val mapBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.world_map, BitmapFactory.Options().also { it.inMutable = true })
        val canvas = Canvas(mapBitmap)
        val radius = minOf(canvas.width, canvas.height) / 50f
        val x = (longitude + 180f) / 360f * canvas.width
        val y = (1f - (latitude + 90f) / 180f) * canvas.height
        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = 25
        canvas.drawCircle(x, y, radius, paint)
        val mapImageBitmap = mapBitmap.asImageBitmap()
        while (true) {
            value = originalImageBitmap
            delay(1000L)
            value = mapImageBitmap
            delay(1000L)
        }
    }
}

@Composable
fun MapDisplayer(bitmap: ImageBitmap?) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asAndroidBitmap().asImageBitmap(),
                contentDescription = "Location ",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(2f)
            )
        }
}

@Composable
fun flag(name: String){
    when(name){
        "France"    -> {Flags().FranceFlag()}
        "Japan"     -> Flags().JapanFlag()
        "Qatar"     -> Flags().QatarFlag()
        "Germany"   -> Flags().GermanyFlag()
    }
}
