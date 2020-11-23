package example

import com.amazonaws.services.ec2.model.{DescribeRegionsRequest, Filter}
import example.Model.{Account, Accounts}
import io.circe.Encoder.AsObject.importedAsObjectEncoder
import io.circe._
import io.circe.generic.auto.exportEncoder
import io.circe.syntax.EncoderOps

import scala.io.Source
import scala.jdk.CollectionConverters.CollectionHasAsScala
import scala.util.control.NonFatal

object OptedInRegion {
  def main(args: Array[String]): Unit = {
    args.toList match {
      case Nil => {
        println("error - missing argument <output file>")
        sys.exit(1)
      }
      // TODO: add some code that allows you to EITHER pass in a list OR a file name and then processes them appropriately
      // get the list of comma separated accounts OR get the file name from the parameter
      case accounts :: _ => {
        // create a Scala list from the list of comma separated accounts
        val listOfAccounts: List[String] = accounts.split(",").toList
        // if the accounts are in a CSV file - loop through the list of accounts in the file
        // val parsedListOfAccounts = getAccounts(listOfAccounts)
        // run the describe regions API on each account
        val optedInRegions = discoverAllOptInRegions(listOfAccounts)
        // convert the result into Json
        // val jsonResponse = resultToJson(optedInRegions)
        // print to the command line
        //TODO: should we print the successes from the Either as JSON and the failures as printlns to STDOUT?
        //TODO: OR, should the model change to encapsulate fails and include error messages?
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

  def discoverOptInRegions(account: String): Either[String, List[String]] = {
    val ec2Client = Clients.createClient(account)
    try {
        val request = new DescribeRegionsRequest()
          .withAllRegions(true)
          .withFilters(new Filter("opt-in-status").withValues("opted-in"))
        Right(ec2Client.describeRegions(request).getRegions.asScala.toList.map(_.getRegionName))
      } catch {
      case NonFatal(error) => {
        println("There's an error! We may not have been able to make the DescribeRegionsRequest to AWS", error)
        Left(error.getMessage)
      }
    } finally {
        ec2Client.shutdown()
      }
  }

  def discoverAllOptInRegions(accounts: List[String]): Map[String, Either[String, List[String]]] = {
    accounts.map(account => account -> discoverOptInRegions(account)).toMap
  }

  // split map into two (pattern match): successes to json and print failures
  def getDiscoverOptInRegionResults(discoverRegionsResults: Map[String, Either[String, List[String]]]): Unit = {
    discoverRegionsResults match {
      case right: Map[accountName, Right[_, _]] => ??? // pass this to the toJson function?
      case left: Map[_, Left[_, _]] => ??? // println this?
    }
  }

  // TODO: there must be a better way of doing this!
  def toJson(successfulResult: Map[String, List[String]]): Json = {
    Accounts(successfulResult.map(result => Account(result._1, result._2)).toList).asJson
  }

  //TODO: is it worth declaring some case classes like this to make the type signatures easier to understand above?
  case class OptInRegions(optInRegions: List[String])

  case class AccountName(name: String)

  case class AccountWithOptedInRegions(account: Map[AccountName, Either[String, OptInRegions]])
}