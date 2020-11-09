package example

import io.circe._
import scala.io.Source

// TODO: https://gist.github.com/katebee/be5635bb5426f569b16cccc5a87386ae

object OptedInRegion {
  def main(args: Array[String]): Unit = {
    args.toList match {
      case Nil => {
        println("error - missing argument <output file>")
        sys.exit(1)
      }
      case inputFile :: _ => {
        // get the file name from the parameter
        val listOfAccounts = inputFile
        // loop through the list of accounts in the file
        val parsedListOfAccounts = getAccounts(listOfAccounts)
        // this function takes an account and returns a client
        val optedInRegions = discoverOptInRegions(parsedListOfAccounts)
        // convert the result into Json
        val jsonResponse = resultToJson(optedInRegions)
        // print to the command line
        println(jsonResponse)
      }
    }
  }

  def getAccounts(file: String): List[String] = {
    val bufferedSource = Source.fromFile(file)
    val result = bufferedSource.mkString.split(",").toList
    bufferedSource.close
    result
  }

  // map through each account in the list
  // run the describe regions function on each account
  // get the client for each account from the Clients.createClient function
  // return a Map of account to list of opted in regions
  def discoverOptInRegions(accounts: List[String]): Map[String, List[String]] = ???

  // take the Map of account to list of opted in regions and return Json
  // what should this Json look like?
  def resultToJson(result: Map[String, List[String]]): Json = ???
}