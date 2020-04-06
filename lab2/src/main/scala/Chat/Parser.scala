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
      (token :: more.toList).mkString(" or ") +
      ", found: " + curToken)

  def fatalError(msg: String): Nothing = {
    println("Fatal error", msg)
    new Exception().printStackTrace()
    sys.exit(1)
  }

  /**
    * Handle identification case class
    * @return a Pseudonym case class with the pseudonym value
    */
  def handleIdentification(): ExprTree = {
    val name = curValue
    readToken()
    UsersInfo.registerUser(name)
    Pseudonym(name)
  }

  def getBeerBrand: (String, Double) =
  // Search for brands
    curToken match {
      case FARMER => Products.beers("farmer")
      case BOXER => Products.beers("")
      case WITTEKOP => Products.beers("wittekop")
      case PUNKIPA => Products.beers("punkipa")
      case JACKHAMMER => Products.beers("jackhammer")
      case TENEBREUSE => Products.beers("tenebreuse")
      case _ => Products.beers("")
    }

  def getCroissantBrand: (String, Double) =
  // Search for brands
    curToken match {
      case MAISON => Products.croissants("")
      case CAILLER => Products.croissants("cailler")
      case _ => Products.croissants("")
    }

  def parseCommand: ExprTree = {
    // TODO: refactor!!
    if (curToken == NUM) {
      val numberOfProducts = curValue.toInt
      readToken()
      curToken match {
        case BIERE =>
          readToken()
          if (curToken != EOL && !(curToken == ET || curToken == OU)) {
            val beer = getBeerBrand
            readToken()
            curToken match {
              case ET =>
                readToken()
                And(Command(beer, numberOfProducts), parseCommand)
              case OU =>
                readToken()
                Or(Command(beer, numberOfProducts), parseCommand)
              case _ => Command(beer, numberOfProducts)
            }
          } else {
            val beer = Products.beers("")
            curToken match {
              case ET =>
                readToken()
                And(Command(beer, numberOfProducts), parseCommand)
              case OU =>
                readToken()
                Or(Command(beer, numberOfProducts), parseCommand)
              case _ => Command(beer, numberOfProducts)
            }
          }
        case CROISSANT =>
          readToken()
          if (curToken != EOL && !(curToken == ET || curToken == OU)) {
            val croissant = getCroissantBrand
            readToken()
            curToken match {
              case ET =>
                readToken()
                And(Command(croissant, numberOfProducts), parseCommand)
              case OU =>
                readToken()
                Or(Command(croissant, numberOfProducts), parseCommand)
              case _ => Command(croissant, numberOfProducts)
            }
          } else {
            val croissant = Products.croissants("")
            curToken match {
              case ET =>
                readToken()
                And(Command(croissant, numberOfProducts), parseCommand)
              case OU =>
                readToken()
                Or(Command(croissant, numberOfProducts), parseCommand)
              case _ => Command(croissant, numberOfProducts)
            }
          }
        case _ => expected(BIERE, CROISSANT)
      }
    } else {
      expected(NUM)
    }
  }

  /** the root method of the parser: parses an entry phrase */
  def parsePhrases() : ExprTree = {
    // TODO: handle user identification for commands and account inquiries
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
          eat(COMMAND)
          // TODO: recursive function for order to handle and / or
          parseCommand
      }
    }
    else expected(BONJOUR, JE)
  }

  // Start the process by reading the first token.
  readToken()
}
