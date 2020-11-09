package example

class Model {

  case class Account(profileName: String, optedInRegions: List[String])

  case class Accounts(accountsWithOptedInRegions: List[Account])
}