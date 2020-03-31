package Chat

object Tokens {
  type Token = Int

  // Terms
  val BONJOUR: Token     = 0
  val JE: Token          = 1
  // Actions
  val ETRE: Token        = 2
  val VOULOIR: Token     = 3
  // Operators
  val ET: Token          = 4
  val OU: Token          = 5
  // Products
  val BIERE: Token       = 6
  val CROISSANT: Token   = 7
  // Utils
  val PSEUDO: Token      = 9
  val NUM: Token         = 10
  val UNKNOWN: Token     = 11
  val EOL: Token         = 12
  // State of mind
  val ASSOIFFE : Token   = 13
  val AFFAME : Token     = 14
  // Brand
  val MAISON: Token      = 15
  val CAILLER: Token     = 16
  val FARMER: Token      = 17
  val BOXER: Token       = 18
  val WITTEKOP: Token    = 19
  val PUNKIPA: Token     = 20
  val JACKHAMMER: Token  = 21
  val TENEBREUSE: Token  = 22
  // Identification
  val ME: Token          = 25
  val APPELLE: Token     = 26
  // Command
  val COMMAND: Token     = 27
  // Balance
  val CONNAITRE: Token   = 28
  val SOLDE: Token       = 29
  // Price
  val QUEL: Token        = 30
  val EST: Token         = 31
  val LE: Token          = 32
  val PRIX: Token        = 33
  val DE: Token          = 34
  val COMBIEN: Token     = 35
  val COUTE: Token       = 36
}
