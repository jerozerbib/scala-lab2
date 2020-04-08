package Data

import scala.collection.mutable

object UsersInfo {

  // Will contain the name of the currently active user; default value is null.
  private var _activeUser: String = _

  private val accounts = collection.mutable.Map[String, Double]()

  /**
    * Check if there is an active user.
    * @return true if there is an active user
    */
  def thereIsAnActiveUser(): Boolean = {
    _activeUser != null
  }

  /**
    * Get the account balance of a user.
    * @return the account balance of a user
    */
  def getUserAccount: Double = accounts(_activeUser)

  /**
    * Register a new user with a default account balance of 30 CHF.
    * @param user the user to save
    */
  def registerUser(user: String): Unit = {
    if (!accounts.contains(user)) {
      accounts(user) = 30
    }
    _activeUser = user
  }

  /**
    * Carry out a purchase on the active user
    * @param amount the value of the purchase
    * @return the current account balancer of the active user after purchase
    */
  def activeUserPurchase(amount: Double): Double = purchase(_activeUser, amount)

  /**
    * Update an account by decreasing its balance.
    * @param user the user whose account will be updated
    * @param amount the amount to decrease
    * @return the new balance
    */
  def purchase(user: String, amount: Double): Double = {
    val newAmount  = accounts(user) - amount
    accounts(user) = newAmount
    newAmount
  }
}
