package fr.uge.android.flags.ui.theme

data class Rank(val position: Int, val maxPosition: Int) {
    val ratio get() = (maxPosition.toFloat() - position.toFloat()) / (maxPosition.toFloat() - 1f)
}

sealed class QuantitativeFact(val name: String,val value: Float, val rank: Rank) {
    abstract val unit: String
}
class AreaFact(name: String = "Area",value: Float, rank: Rank): QuantitativeFact(name, value, rank) { override val unit = "km^2" }
class PopulationFact(name: String = "Population",value: Float, rank: Rank): QuantitativeFact(name, value, rank) { override val unit = ""}
class DensityFact (name: String = "Density",value: Float, rank: Rank): QuantitativeFact(name, value, rank) { override val unit = "p/km^2"}
class PerCapitaGDPFact (name: String = "GDP per capita",value: Float, rank: Rank): QuantitativeFact(name,value, rank) { override val unit = "$/p"}


