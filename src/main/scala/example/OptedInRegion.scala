package example

import com.amazonaws.services.ec2.model.{DescribeRegionsRequest, Filter}
import io.circe._

import scala.io.Source
import scala.jdk.CollectionConverters.CollectionHasAsScala

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
        // run the describe regions API on each account
        // take the client for each account from Clients.scala
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

  // TODO: double check my filter is working
  // https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/ec2/model/Filter.html#Filter--
  // https://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_Filter.html
  // TODO: ideally I want to get a Map of account name to list of regions - how can I do that?
  // map through list of accounts and for each of them:
  // get the client
  // run the describe regions function
  // return a Map of account to list of opted in regions
  def discoverOptInRegions(accounts: List[String]): Map[String, List[String]] = {
    val ec2Client = accounts.map(Clients.createClient)
    try {
        val request = new DescribeRegionsRequest()
          .withAllRegions(true)
          .withFilters(new Filter("opt-in-status").withValues("opted-in"))
        val responses = ec2Client.map(_.describeRegions(request))
        // responses.map(_.getRegions)
        // responses.map(_.getRegions.asScala.toList.map(_.getRegionName))
      }
      finally {
        ec2Client.shutdown()
      }
  }

  // take the Map of account to list of opted in regions and return Json
  def resultToJson(result: Map[String, List[String]]): Json = ???
}