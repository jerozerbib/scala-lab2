package Data

object Products {
  // TODO: step 2 - here your will have an attribute that will contain the products (e.g. "bière"), their types (e.g. "Boxer"), and their prices (e.g. 2.0).
  // TODO: step 2 - You will also have to find a way to store the default type/brand of a product.

  // Can access using map operations. For example products("beers")("punkipa") to access punk ipa beer
  val beers: Map[String, (String, Int)] = Map(
    "farmer" -> ("Farmer", 1),
    "wittekop" -> ("Wittekop",2),
    "punkipa" -> ("PunkIPA",3),
    "jackhammer" -> ("Jackhammer",3),
    "tenebreuse" -> ("Ténébreuse",4)
  ).withDefaultValue(("Boxer", 1))

  val croissants: Map[String, (String, Int)] = Map(
    "cailler" -> ("Cailler" -> 2)
  ).withDefaultValue("Maison" -> 2)

  val products = Map(
    "beers" -> beers,
    "croissants" -> croissants
  )
}
