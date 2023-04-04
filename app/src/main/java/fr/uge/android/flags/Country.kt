package fr.uge.android.flags

import androidx.compose.runtime.Composable
import fr.uge.android.flags.ui.theme.*
import java.util.TimeZone

class Country(val name:String,val facts:List<QuantitativeFact>) {

    lateinit var tz: TimeZone

      var resourceId: Int = 0

     var geoCoordinates  = arrayOf(0f,0f)

    @Composable
    fun getFlag(type: @Composable () -> Unit) {
        type()
    }
}

val franceFacts = listOf(AreaFact(value = 543940f, rank = Rank(50,195))
    , PopulationFact(value = 67960000f, rank = Rank(20,195))
    , DensityFact(value = 117f, rank = Rank(99,248))
    ,PerCapitaGDPFact(value = 42330f, rank = Rank(24,192)) )

val japanFacts = listOf(AreaFact(value = 377976f, rank = Rank(62,195))
    , PopulationFact(value = 125927902f, rank = Rank(11,195))
    , DensityFact(value = 330f, rank = Rank(41,248))
    ,PerCapitaGDPFact(value = 34358f, rank = Rank(28,192)) )

val qatarFacts = listOf(AreaFact(value = 11586f, rank = Rank(158,195))
    , PopulationFact(value = 2799202f, rank = Rank(137,195))
    , DensityFact(value = 232f, rank = Rank(57,248))
    ,PerCapitaGDPFact(value = 82887f, rank = Rank(5,192)) )

val germanFacts = listOf(AreaFact(value = 357114f, rank = Rank(63,195))
    , PopulationFact(value = 84079811f, rank = Rank(19,195))
    , DensityFact(value = 234f, rank = Rank(61,248))
    ,PerCapitaGDPFact(value =48398f, rank = Rank(18,192)) )


fun France(): Country{
    var fr = Country("France", franceFacts)
    fr.tz = TimeZone.getTimeZone("Europe/Paris")
    fr.geoCoordinates[0] = 48.866667f
    fr.geoCoordinates[1] = 2.333333f
    fr.resourceId= R.drawable.france
    return fr
}

fun Japan(): Country {
    var jap = Country("Japan", japanFacts)
    jap.tz = TimeZone.getTimeZone("Asia/Tokyo")
    jap.geoCoordinates[0] = 35.6894f
    jap.geoCoordinates[1] = 139.692f
    jap.resourceId = R.drawable.japan
    return jap
}

fun Qatar(): Country {
    var qat = Country("Qatar", qatarFacts)
    qat.tz = TimeZone.getTimeZone("Asia/Qatar")
    qat.geoCoordinates[0] = 26.135675f
    qat.geoCoordinates[1] = 51.203324f
    qat.resourceId = R.drawable.qatar
    return qat
}

fun Germany(): Country {
    var germ =Country("Germany", germanFacts)
    germ.tz = TimeZone.getTimeZone("Europe/Berlin")
    germ.resourceId = R.drawable.germany
    germ.geoCoordinates[0] = 52.520007f
    germ.geoCoordinates[1] = 13.404954f
    return germ
}


fun countries(): List<Country>{
    val countries = listOf(France(), Germany(), Japan(), Qatar())
    return countries
}


