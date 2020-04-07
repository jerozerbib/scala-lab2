package Chat

import Data.UsersInfo

// TODO - step 3
object Tree {

  /**
    * This sealed trait represents a node of the tree and contains methods to compute it and write its output text in console.
    */
  sealed trait ExprTree {
    /**
      * Compute the price of the current node, then returns it. If the node is not a computational node, the method
      * returns 0.0.
      * For example if we had a "+" node, we would add the values of its two children, then return the result.
      * @return the result of the computation
      */
    def computePrice: Double = this match {
      case And(e1, e2) => e1.computePrice + e2.computePrice
      case Or(e1, e2) => Math.min(e1.computePrice, e2.computePrice)     // Or takes the cheapest of all products
    }

    /**
      * Return the output text of the current node, in order to write it in console.
      * @return the output text of the current node
      */
    def reply: String = this match {
      // Example cases
      case Thirsty() => "Eh bien, la chance est de votre côté, car nous offrons les meilleures bières de la région !"
      case Hungry() => "Pas de soucis, nous pouvons notamment vous offrir des croissants faits maisons !"
      case Pseudonym(pseudo: String) => "Bonjour " + pseudo

      case Command(e) =>
        // Check if user is identified first
        if (!UsersInfo.thereIsAnActiveUser()) {
          "Veuillez d'abord vous identifier."
        } else {
          // Compute the price of the purchase
          val orderPrice = e.computePrice
          // Check if the user has enough in his account
          if (orderPrice > UsersInfo.getUserAccount) {
            "Désolé, on peut pas vour servir. Vous avez pas assez dans votre compte."
          } else {
            val newAccountBalance = UsersInfo.activeUserPurchase(orderPrice)
            "Bah voilà " + e.reply + " pour un total de " + orderPrice + " CHF et " +
              "votre nouveau solde et de " + newAccountBalance + " CHF. Enjoy!"
          }
        }
      case Beer((name, _), numberOfProducts) => numberOfProducts +
        (if (numberOfProducts > 1) " bières " else " bière " ) + name
      case Croissant((name, _), numberOfProducts) => numberOfProducts +
        (if (numberOfProducts > 1) " croissants " else " croissant " ) + name
      case And(e1, e2) => e1.reply + " et " + e2.reply
      case Or(e1, e2) => if (e1.computePrice < e2.computePrice) e1.reply else e2.reply
      case Balance() =>
        if (!UsersInfo.thereIsAnActiveUser())
          "Veuillez d'abord vous authentifier"
        else
          "Le montant actuel de votre solde est de " + UsersInfo.getUserAccount
      case Price(e) => e.reply + " pour un total de : " + e.computePrice + "CHF"
    }
  }

  /**
    * Declarations of the nodes' types.
    */
  // Example cases
  case class Thirsty() extends ExprTree
  case class Hungry() extends ExprTree

  // Identification
  case class Pseudonym(pseudo: String) extends ExprTree

  case class Command(products: ExprTree) extends ExprTree

  class Product(product: (String, Double), numberOfProducts: Int) extends ExprTree {
    override def computePrice: Double = product._2 * numberOfProducts
  }

  case class Beer(product: (String, Double), numberOfProducts: Int) extends Product(product, numberOfProducts)
  case class Croissant(product: (String, Double), numberOfProducts: Int) extends Product(product, numberOfProducts)

  case class And(e1: ExprTree, e2: ExprTree) extends ExprTree
  case class Or(e1: ExprTree, e2: ExprTree) extends ExprTree

  // TODO (Jeremy)
  case class Price(products: ExprTree) extends ExprTree
  case class Balance() extends ExprTree

}

