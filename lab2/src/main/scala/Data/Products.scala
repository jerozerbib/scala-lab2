package Data

object Products {
  /**
    * Can access using map operations. For example products("beers")("punkipa") to access punk ipa beer
    */
  val beers: Map[String, (String, Double)] = Map(
    "farmer"     -> ("Farmer", 1.0),
    "wittekop"   -> ("Wittekop",2.0),
    "punkipa"    -> ("PunkIPA",3.0),
    "jackhammer" -> ("Jackhammer",3.0),
    "tenebreuse" -> ("Ténébreuse",4.0)
  ).withDefaultValue(("Boxer", 1.0))

  /**
    * Can access using map operations. For example products("croissant")("cailler") to access cailler croissant
    */
  val croissants: Map[String, (String, Double)] = Map(
    "cailler" -> ("Cailler" -> 2.0)
  ).withDefaultValue("Maison" -> 2.0)

  val products = Map(
    "beers"      -> beers,
    "croissants" -> croissants
  )
}
