package Chat

object Tokens {
  type Token = (Int, String)

  // Terms
  val BONJOUR: Token     = (0, "BONJOUR")
  val JE: Token          = (1, "JE")
  // Actions
  val ETRE: Token        = (2, "ETRE")
  val VOULOIR: Token     = (3, "VOULOIR")
  // Operators
  val ET: Token          = (4, "ET")
  val OU: Token          = (5, "OU")
  // Products
  val BIERE: Token       = (6, "BIERE")
  val CROISSANT: Token   = (7, "CROISSANT")
  // Utils
  val PSEUDO: Token      = (8, "PSEUDO")
  val NUM: Token         = (9, "NUM")
  val UNKNOWN: Token     = (10, "UNKNOWN")
  val EOL: Token         = (11, "ELO")
  // State of mind
  val ASSOIFFE : Token   = (12, "ASSOIFFÉ")
  val AFFAME : Token     = (13, "AFFAME")
  // Brand
  val MAISON: Token      = (14, "MAISON")
  val CAILLER: Token     = (15, "CAILLER")
  val FARMER: Token      = (16, "FARMER")
  val BOXER: Token       = (17, "BOXER")
  val WITTEKOP: Token    = (18, "WIKKETOP")
  val PUNKIPA: Token     = (19, "PUNKIPA)")
  val JACKHAMMER: Token  = (20, "JACKHAMMER")
  val TENEBREUSE: Token  = (21, "TENEBREUSE")
  // Identification
  val ME: Token          = (22, "ME")
  val APPELLE: Token     = (23, "APPELLE")
  // Command
  val COMMAND: Token     = (24, "COMMAND")
  // Balance
  val CONNAITRE: Token   = (25, "CONNAITRE")
  val SOLDE: Token       = (26, "SOLDE")
  // Price
  val QUEL: Token        = (27, "QUEL")
  val EST: Token         = (28, "EST")
  val LE: Token          = (29, "LE")
  val PRIX: Token        = (30, "PRIX")
  val DE: Token          = (31, "DE")
  val COMBIEN: Token     = (32, "COMBIEN")
  val COUTE: Token       = (33, "COUTE")
  val COUTENT: Token     = (33, "COUTENT")
}
