package example

import com.amazonaws.services.ec2.model.{DescribeRegionsRequest, Filter}

import scala.jdk.CollectionConverters.CollectionHasAsScala
import scala.util.control.NonFatal

object DiscoverOptInRegion {
  def discoverOptInRegions(account: String): Either[String, List[String]] = {
    val ec2Client = Client.createClient(account)
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
    accounts.map(account => account -> DiscoverOptInRegion.discoverOptInRegions(account)).toMap
  }
}
