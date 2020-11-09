package example

import com.amazonaws.services.ec2.AmazonEC2ClientBuilder
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement
import io.circe._

object OptedInRegion {
  def main(args: Array[String]): Unit = {
    // get the file name from the parameter
    val listOfAccounts = args.head
    // loop through the list of accounts in the file
    val eachAccount = getAccounts(listOfAccounts)
    // create AWS client for each account (I wonder if this function should return a map of account -> client?)
    val accountWithClient = createClient(eachAccount)
    // pass each account to the describe regions function
    val optedInRegions = discoverOptInRegions(accountWithClient)
    // convert the result into Json
    val jsonResponse = resultToJson(optedInRegions)
    // print to the command line
    println(jsonResponse)
  }

  // take in a file name
  // loop through the list in the file (separated by new lines?)
  // produce a list of strings
  def getAccounts(file: String): List[String] = ???

  // take in a list of account names
  // map through each of them to get the account's client
  // return a Map of account to client
  def createClient(accounts: List[String]): Map[String, AmazonIdentityManagement] = ???

  // take in a Map of account to client
  // run the describe regions function on each account with it's own client, checking for opted-in regions
  // return a Map of account to list of opted in regions
  def discoverOptInRegions(accountWithClient: Map[String, AmazonIdentityManagement]): Map[String, List[String]] = ???

  // take the Map of account to list of opted in regions and return Json
  // what should this Json look like?
  def resultToJson(result: Map[String, List[String]]): Json = ???
}