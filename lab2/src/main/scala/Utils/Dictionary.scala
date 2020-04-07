package Utils

/**
* Contains the dictionary of the application, which is used to validate, correct and normalize words entered by the
* user.
*/
object Dictionary {
  // This dictionary is a Map object that contains valid words as keys and their normalized equivalents as values (e.g.
  // we want to normalize the words "veux" and "aimerais" in on unique term: "vouloir").
  val dictionary: Map[String, String] = Map(
    "bonjour" -> "bonjour",
    "hello" -> "bonjour",
    "yo" -> "bonjour",
    "je" -> "je",
    "j" -> "je",
    "me" -> "me",
    "m" -> "me",
    "appelle" -> "appelle",
    "suis" -> "etre",
    "veux" -> "vouloir",
    "aimerais" -> "vouloir",
    "voudrais" -> "vouloir",
    "bière" -> "biere",
    "bières" -> "biere",
    "croissant" -> "croissant",
    "croissants" -> "croissant",
    "et" -> "et",
    "ou" -> "ou",
    "assoiffé" -> "assoiffe",
    "assoiffée" -> "assoiffe",
    "affamé" -> "affame",
    "affamée" -> "affame",
    "maison" -> "maison",
    "cailler" -> "cailler",
    "farmer" -> "farmer",
    "boxer" -> "boxer",
    "wittekop" -> "wittekop",
    "punkipa" -> "punkipa",
    "jackhammer" -> "jackhammer",
    "tenebreuse" -> "tenebreuse",
    "commande" -> "commande",
    "connaitre" -> "connaître",
    "connaître" -> "connaître",
    "combien" -> "combien",
    "coûte" -> "coûte",
    "coûtent" -> "coûte",
    "quel" -> "quel",
    "quelle" -> "quel",
    "est" -> "est",
    "sont" -> "est",
    "le" -> "le",
    "la" -> "le",
    "prix" -> "prix",
    "de" -> "de",
    "des" -> "de",
    "mon" -> "mon",
    "ma" -> "mon",
    "mes" -> "mon"
  )
}
