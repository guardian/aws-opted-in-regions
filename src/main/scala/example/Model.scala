package example

object Model {
  case class Account(profileName: String, optedInRegions: List[String], status: String, message: Option[String])

  case class Arguments(listOfAccounts: Option[String], file: Option[String])

  object Arguments {
    def empty() = Arguments(Some(""), Some(""))
  }

  //TODO: we could get rid of this and just call List[Account] instead of having a case class
  case class Accounts(accountsWithOptedInRegions: List[Account])

  //TODO: consider using these case classes earlier if easier for me to read
  case class OptInRegions(optInRegions: List[String])

  case class AccountName(name: String)

  case class AccountWithOptedInRegions(account: Map[AccountName, Either[String, OptInRegions]])
}
