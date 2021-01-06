package example

import example.Model.{Account, AccountWithOptedInRegions}
import io.circe.Json

object Result {
  //TODO: not sure we still need this function?
  // split map into two (pattern match): successes to json and print failures
//  def getDiscoverOptInRegionResults(discoverRegionsResults: AccountWithOptedInRegions): Unit = {
//    discoverRegionsResults match {
//      case right: Map[accountName, Right[_, _]] => ??? // pass this to the toJson function?
//      case left: Map[_, Left[_, _]] => ??? // println this?
//      case _ => ???
//    }
//  }

  //TODO: complete
  //TODO: consider happy/unhappy case of the response to AWS. Does it fit the type this function expects?
  //TODO: write test
  def parseResponse(response: AccountWithOptedInRegions): List[Account] = ???

  //TODO: complete
  // TODO: write test for this function (mock an output from the API and test we're formatting it correctly)
  def toJson(parsedResponse: List[Account]): Json = {
    ???
    //Accounts(parsedResponse.map(result => Account(result._1, result._2)).toList).asJson
  }
}
