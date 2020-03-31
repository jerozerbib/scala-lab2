package Data

import scala.collection.mutable

object UsersInfo {

  // Will contain the name of the currently active user; default value is null.
  private var _activeUser: String = _

  // TODO: step 2 - create an attribute that will contain each user and its current balance.
  private var accounts = collection.mutable.Map[String, Double]()

  /**
    * Register a new user with a default account balance of 30 CHF.
    * @param user the user to save
    */
  def registerUser(user: String): Unit = {
    if (accounts.contains(user)) {
      throw new Error("User " + user + " is already registered")
    }
    accounts(user) = 30
  }

  /**
    * Update an account by decreasing its balance.
    * @param user the user whose account will be updated
    * @param amount the amount to decrease
    * @return the new balance
    */
  // TODO: step 2
  def purchase(user: String, amount: Double): Double = {
    val newAmount = accounts(user) - amount
    accounts(user) = newAmount
    newAmount
  }
}
