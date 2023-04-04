package fr.uge.android.flags

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable

sealed class MainComponent(var route: String){
    object FlagsComponent: MainComponent(route = "flags"){}
    class CountryComponent(val country: Country): MainComponent(route = country.name){}
}
