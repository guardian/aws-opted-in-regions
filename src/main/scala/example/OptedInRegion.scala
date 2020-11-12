package example

import com.amazonaws.services.ec2.model.{DescribeRegionsRequest, Filter}
import io.circe._

import scala.io.Source
import scala.jdk.CollectionConverters.CollectionHasAsScala
import scala.util.control.NonFatal

// TODO: https://gist.github.com/katebee/be5635bb5426f569b16cccc5a87386ae

object OptedInRegion {
  def main(args: Array[String]): Unit = {
    args.toList match {
      case Nil => {
        println("error - missing argument <output file>")
        sys.exit(1)
      }
      case accounts :: _ => {
        // get the file name from the parameter
        val listOfAccounts: List[String] = accounts.split(",").toList
        // loop through the list of accounts in the file
        // val parsedListOfAccounts = getAccounts(listOfAccounts)
        // run the describe regions API on each account
        val optedInRegions = discoverAllOptInRegions(listOfAccounts)
        // convert the result into Json
        // val jsonResponse = resultToJson(optedInRegions)
        // print to the command line
        println(optedInRegions)
      }
    }
  }

  def getAccounts(file: String): List[String] = {
    val bufferedSource = Source.fromFile(file)
    val result = bufferedSource.mkString.split(",").toList
    bufferedSource.close
    result
  }

  // TODO: https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/ec2/model/Filter.html#Filter--
  // TODO: https://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_Filter.html
  def discoverOptInRegions(account: String): Either[String, List[String]] = {
    val ec2Client = Clients.createClient(account)
    try {
        val request = new DescribeRegionsRequest()
          .withAllRegions(true)
          .withFilters(new Filter("opt-in-status").withValues("opted-in"))
        Right(ec2Client.describeRegions(request).getRegions.asScala.toList.map(_.getRegionName))
      } catch {
      case NonFatal(error) => {
        println(s"${error.getMessage}")
        Left(error.getMessage)
      }
    } finally {
        ec2Client.shutdown()
      }
  }

  def discoverAllOptInRegions(accounts: List[String]): Map[String, Either[String, List[String]]] = {
    accounts.map(account => account -> discoverOptInRegions(account)).toMap
  }

  def split(map: Map[String, Either[String, List[String]]]): (Map[String, List[String]], Map[String, List[String]]) = ???

  // new method
  // split map into two (pattern match)
  // successes to json
  // print failures

  def toJson(result: Map[String, List[String]]): Json = {
    ???
  }
}