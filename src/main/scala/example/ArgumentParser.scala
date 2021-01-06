package example

import example.Model.Arguments
import scopt.OptionParser

object ArgumentParser {
  val argParser = new OptionParser[Arguments]("aws-opted-in-regions") {
    opt[String]("list").optional()
      .action { (list, args) =>
        args.copy(listOfAccounts = Some(list))
      } text "This list of accounts will be run through the aws-opt-in script"
    opt[String]("file").optional()
      .action { (file, args) =>
        args.copy(file = Some(file))
      } text "The list of accounts in the given file will be run through the aws-opt-in script"
    checkConfig { args =>
      (args.listOfAccounts.isEmpty, args.file.isEmpty) match {
        case (true, true) =>
          failure("You must provide either a comma separated list of accounts, or a file containing a list of accounts")
        case (false, false) =>
          failure("You must provide either a comma separated list of accounts, or a file containing a list of accounts (not both)")
        case _ =>
          success
      }
    }
  }
}
