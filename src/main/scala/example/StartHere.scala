package example

object StartHere {
  def main(args: Array[String]): Unit = {
    ArgumentParser.argParser.parse(args, Model.Arguments.empty()) match {
      case Some(Model.Arguments(accountsOpt, fileOpt)) => {
        //TODO: change to match statement
        if (accountsOpt.nonEmpty) {
          // create a Scala list from the list of comma separated accounts
          val listOfAccounts = accountsOpt.get.split(",").toList
          // run the describe regions API on each account
          val optedInRegions = DiscoverOptInRegion.discoverAllOptInRegions(listOfAccounts)
          // print to the command line
          println(optedInRegions)
        } else if (fileOpt.nonEmpty) {
          // loop through the list of accounts in the file
          val parsedFile = Account.getAccountsFromFile(fileOpt.get)
          // run the describe regions API on each account
          val optedInRegions = DiscoverOptInRegion.discoverAllOptInRegions(parsedFile)
          // print to the command line
          println(optedInRegions)
        }
      }
      case None => {
        println("You must provide either a comma separated list of accounts, or a file containing a list of accounts")
        sys.exit(1)
      }
    }
  }
}