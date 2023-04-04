package fr.uge.android.flags
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kotlin.random.Random


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun mainComponent(){
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
                 AppTopBar(
                     onNavItemClick = {
                                      scope.launch {
                                          scaffoldState.drawerState.open()
                                      }
                         },
                     navController = navController)
        },
        bottomBar = { BottomAppBar( modifier = Modifier.height(35.dp)) {

        }},
        drawerContent = {
            drawerContent(items = countries(), onItemClick ={ country ->
                navController.navigate(country.name)
            } )
        }) {
        NavHost(navController = navController, startDestination = MainComponent.FlagsComponent.route){
            listOf(France(), Japan(), Qatar(), Germany()).forEach { country ->
                composable(route = country.name){
                    CountryDisplayer(country = country)
                }
                composable(route = MainComponent.FlagsComponent.route){
                    FlagsDisplayer(countries = countries())
                }
            }
        }
    }
}

@Composable
fun AppTopBar(
    onNavItemClick: () -> Unit,
    navController: NavHostController
){
    TopAppBar(
        title = {
            Text(text = "World Countries", fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())},
    navigationIcon = {Button(onClick = { onNavItemClick() }, modifier = Modifier.width(IntrinsicSize.Min)) {
        Icon(Icons.Default.Menu, "Home")
    }},
    actions = {
        Button(onClick = {
        navController.navigate(MainComponent.FlagsComponent.route) }, modifier = Modifier.width(IntrinsicSize.Min)) {
        Text(text = "Flags", modifier = Modifier.width(50.dp))}
        Spacer(modifier = Modifier.width(5.dp))
        Button(onClick = {
            val rn: Int = Random.nextInt(0,3)
            val cn = listOf(France().name, Japan().name, Germany().name, Qatar().name)
            navController.navigate(cn[rn]) }, modifier = Modifier.width(IntrinsicSize.Min)) {
            Text(text = "Random",maxLines = 1, modifier = Modifier.width(70.dp))}
    }, backgroundColor = Color.Gray, contentColor = Color.White)
}


@Composable
fun drawerContent(
    items : List<Country>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (Country) -> Unit
){
    LazyColumn(modifier){
        items(items) {
                item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable { onItemClick(item) }) {
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)) {
                    item.getFlag {
                        flag(name = item.name)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "${item.name}", modifier = Modifier
                    .weight(5f)
                    .align(alignment = Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}


