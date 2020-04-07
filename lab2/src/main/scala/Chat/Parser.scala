package Chat

import Chat.Tokens._
import Data.{Products, UsersInfo}
import Tree._

// TODO - step 4
class Parser(tokenizer: Tokenizer) {

  import tokenizer._

  var curTuple: (String, Token) = ("unknown", UNKNOWN)

  def curValue: String = curTuple._1

  def curToken: Token = curTuple._2

  /** Reads the next token and assigns it into the global variable curTuple */
  def readToken(): Unit = curTuple = nextToken()

  /** "Eats" the expected token, or terminates with an error. */
  private def eat(token: Token): Unit = if (token == curToken) readToken() else expected(token)

  /** Complains that what was found was not expected. The method accepts arbitrarily many arguments of type TokenClass */
  // TODO (BONUS): find a way to display the string value of the tokens (e.g. "BIERE") instead of their integer value (e.g. 6).
  private def expected(token: Token, more: Token*): Nothing =
    fatalError(" expected: " +
      (token._2 :: more.map(_._2).toList).mkString(" or ") +
      ", found: " + curToken._2)

  def fatalError(msg: String): Nothing = {
    println("Fatal error", msg)
    new Exception().printStackTrace()
    sys.exit(1)
  }

  /**
    * Handle identification case class
    *
    * @return a Pseudonym case class with the pseudonym value
    */
  def handleIdentification(): ExprTree = {
    val name = curValue
    readToken()
    UsersInfo.registerUser(name)
    Pseudonym(name)
  }

  def getBeerBrand(beerBrandToken: Token): (String, Double) =
    beerBrandToken match {
      case FARMER => Products.beers("farmer")
      case BOXER => Products.beers("")
      case WITTEKOP => Products.beers("wittekop")
      case PUNKIPA => Products.beers("punkipa")
      case JACKHAMMER => Products.beers("jackhammer")
      case TENEBREUSE => Products.beers("tenebreuse")
      case _ => Products.beers("")
    }

  def getCroissantBrand(croissantBrandToken: Token): (String, Double) =
    croissantBrandToken match {
      case MAISON => Products.croissants("")
      case CAILLER => Products.croissants("cailler")
      case _ => Products.croissants("")
    }

  def checkForBeerBrand: (String, Double) =
    if (curToken != EOL && !(curToken == ET || curToken == OU)) {
      val brandToken = curToken
      readToken()
      getBeerBrand(brandToken)
    } else {
      Products.beers("")
    }

  def checkForCroissantBrand: (String, Double) =
    if (curToken != EOL && !(curToken == ET || curToken == OU)) {
      val brandToken = curToken
      readToken()
      getCroissantBrand(brandToken)
    } else {
      Products.croissants("")
    }

  def parseCommand: ExprTree = {
    if (curToken == NUM) {
      val numberOfProducts = curValue.toInt
      readToken()
      curToken match {
        case BIERE =>
          readToken()
          // Means there's a brand requested. Note that any token other than eol, et or ou will be considered as a brand.
          // This means if you write anything that doesn't resolve to a brand, it will be interpreted as the default brand
          // e.g : Bonjour je voudrais 2 bières balala => Bière Boxer
          // Bonjour je voudrais 2 bières voudrais => Bière Boxer
          val beer = checkForBeerBrand
          curToken match {
            case ET =>
              readToken()
              And(Beer(beer, numberOfProducts), parseCommand)
            case OU =>
              readToken()
              Or(Beer(beer, numberOfProducts), parseCommand)
            case _ => Beer(beer, numberOfProducts)
          }
        case CROISSANT =>
          readToken()
          val croissant = checkForCroissantBrand
          curToken match {
            case ET =>
              readToken()
              And(Croissant(croissant, numberOfProducts), parseCommand)
            case OU =>
              readToken()
              Or(Croissant(croissant, numberOfProducts), parseCommand)
            case _ => Croissant(croissant, numberOfProducts)
          }
        case _ => expected(BIERE, CROISSANT)
      }
    } else {
      expected(NUM)
    }
  }

  /** the root method of the parser: parses an entry phrase */
  def parsePhrases(): ExprTree = {
    // TODO Jeremy: Handle account inquiries and prices inquiries
    if (curToken == BONJOUR) eat(BONJOUR)
    if (curToken == JE) {
      eat(JE)
      curToken match {
        case ETRE =>
          eat(ETRE)
          curToken match {
            case ASSOIFFE =>
              // Here we do not "eat" the token, because we want to have a custom 2-parameters "expected" if the user gave a wrong token.
              readToken()
              Thirsty()
            case AFFAME =>
              readToken()
              Hungry()
            case PSEUDO =>
              handleIdentification()
            case _ => expected(ASSOIFFE, AFFAME, PSEUDO)
          }
        case ME =>
          eat(ME)
          eat(APPELLE)
          if (curToken == PSEUDO) {
            handleIdentification()
          } else {
            expected(PSEUDO)
          }
        case VOULOIR =>
          eat(VOULOIR)
          if (curToken == COMMAND) {
            eat(COMMAND)
            Command(parseCommand)
          } else if (curToken == CONNAITRE) {
            eat(CONNAITRE)
            if (curToken != MON)
              expected(MON)
            eat(MON)
            if (curToken != SOLDE)
              expected(SOLDE)
            eat(SOLDE)
            Balance()
          } else {
            expected(CONNAITRE, COMMAND)
          }
        case _ => expected(ETRE, ME, VOULOIR)
      }
    }
    if (curToken == COMBIEN) {
      eat(COMBIEN)
      curToken match {
        case COUTE =>
          eat(COUTE)
          Price(parseCommand)
        case COUTENT =>
          eat(COUTENT)
          Price(parseCommand)
        case _ => expected(COUTENT, COUTE)
      }
    }
    if (curToken == QUEL) {
      eat(QUEL)
      if (curToken != EST)
        expected(EST)
      eat(EST)
      if (curToken != LE)
        expected(LE)
      eat(LE)
      if (curToken != PRIX)
        expected(PRIX)
      eat(PRIX)
      if (curToken != DE)
        expected(DE)
      eat(DE)
      Price(parseCommand)
    }
    else expected(BONJOUR, JE, COMBIEN, QUEL)
  }

  // Start the process by reading the first token.
  readToken()
}

