package Chat

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
      case Product((_, value), numberOfProducts) => numberOfProducts * value
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

      case Command(e) => "Bah voilà " + e.reply + " pour un total de " + e.computePrice + " CHF. Enjoy!"
      case Product((name, _), numberOfProducts) => numberOfProducts + " " + name
      case And(e1, e2) => e1.reply + " et " + e2.reply
        // TODO: Right or left Associativity ???
      case Or(e1, e2) => if (e1.computePrice < e2.computePrice) e1.reply else e2.reply
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
  case class Product(product: (String, Double), numberOfProducts: Int) extends ExprTree
  // TODO: inheritance of case classes ???
  //case class Beer(product: (String, Double), numberOfProducts: Int) extends Product
  //case class Croissant(product: (String, Double), numberOfProducts: Int) extends Product

  case class And(e1: ExprTree, e2: ExprTree) extends ExprTree
  case class Or(e1: ExprTree, e2: ExprTree) extends ExprTree

}
