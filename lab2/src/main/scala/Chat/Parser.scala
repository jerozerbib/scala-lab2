package Chat

import Chat.Tokens._
import Data.{Products, UsersInfo}
import Tree._

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

  /**
    * Gets the beer's brand's name and price
    * @param beerBrandToken Token
    * @return A tuple with the name and the price of a beer
    */
  def getBeerBrand(beerBrandToken: Token): (String, Double) =
    beerBrandToken match {
      case FARMER     => Products.beers("farmer")
      case BOXER      => Products.beers("")
      case WITTEKOP   => Products.beers("wittekop")
      case PUNKIPA    => Products.beers("punkipa")
      case JACKHAMMER => Products.beers("jackhammer")
      case TENEBREUSE => Products.beers("tenebreuse")
      case _          => Products.beers("")
    }

  /**
    * Gets the croissant's brand's name and price
    * @param croissantBrandToken Token
    * @return A tuple with the name and the price of a croissant
    */
  def getCroissantBrand(croissantBrandToken: Token): (String, Double) =
    croissantBrandToken match {
      case MAISON   => Products.croissants("")
      case CAILLER  => Products.croissants("cailler")
      case _        => Products.croissants("")
    }

  /**
    * Checks if the token is a beer brand
    * @return The product if a beer is asked for
    */
  def checkForBeerBrand: (String, Double) =
    if (curToken != EOL && !(curToken == ET || curToken == OU)) {
      val brandToken = curToken
      readToken()
      getBeerBrand(brandToken)
    } else {
      Products.beers("")
    }

  /**
    * Checks if the token is a croissant brand
    * @return The product if a croissant is asked for
    */
  def checkForCroissantBrand: (String, Double) =
    if (curToken != EOL && !(curToken == ET || curToken == OU)) {
      val brandToken = curToken
      readToken()
      getCroissantBrand(brandToken)
    } else {
      Products.croissants("")
    }

  /**
    * Build the Tree from the command asked for by a customer
    * @return A tree for a command
    */
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
          buildProductsAssociations(beer, numberOfProducts, createBeerNode)
        case CROISSANT =>
          readToken()
          val croissant = checkForCroissantBrand
          buildProductsAssociations(croissant, numberOfProducts, createCroissantNode)
        case _ => expected(BIERE, CROISSANT)
      }
    } else {
      expected(NUM)
    }
  }

  def buildProductsAssociations[P <: Product](product: (String, Double),
                                              nbProducts: Int,
                                              f: ((String, Double), Int) => P): ExprTree = {
    curToken match {
      case ET =>
        readToken()
        And(f(product, nbProducts), parseCommand)
      case OU =>
        readToken()
        Or(f(product, nbProducts), parseCommand)
      case _ => f(product, nbProducts)
    }
  }

  def createBeerNode(product: (String, Double), nbProducts: Int): Beer = Beer(product, nbProducts)
  def createCroissantNode(product: (String, Double), nbProducts: Int): Croissant = Croissant(product, nbProducts)

  /**
    * the root method of the parser: parses an entry phrase
    * @return A Tree with the sentence of the command
    */
  def parsePhrases(): ExprTree = {
    if (curToken == BONJOUR) eat(BONJOUR)

    curToken match {
      case JE =>
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
              eat(MON)
              eat(SOLDE)
              Balance()
            } else {
              expected(CONNAITRE, COMMAND)
            }
          case _ => expected(ETRE, ME, VOULOIR)
        }

      case COMBIEN =>
        eat(COMBIEN)
        eat(COUTE)
        Price(parseCommand)

      case QUEL =>
        eat(QUEL)
        eat(EST)
        eat(LE)
        eat(PRIX)
        eat(DE)
        Price(parseCommand)

      case _ => expected(BONJOUR, JE, COMBIEN, QUEL)
    }
  }

  // Start the process by reading the first token.
  readToken()
}

