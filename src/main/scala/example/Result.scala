package example

import example.Model.Account
import io.circe.Json

object Result {
  // split map into two (pattern match): successes to json and print failures
  // compiler error - let's use case classes:
  // https://stackoverflow.com/questions/25222989/erasure-elimination-in-scala-non-variable-type-argument-is-unchecked-since-it
  def getDiscoverOptInRegionResults(discoverRegionsResults: Map[String, Either[String, List[String]]]): Unit = {
    discoverRegionsResults match {
      case right: Map[accountName, Right[_, _]] => ??? // pass this to the toJson function?
      case left: Map[_, Left[_, _]] => ??? // println this?
      case _ => ???
    }
  }

  //TODO: complete
  //TODO: consider happy/unhappy case of the response to AWS. Does it fit the type this function expects?
  //TODO: write test
  def parseResponse(response: Map[String, List[String]]): List[Account] = ???

  //TODO: complete
  // TODO: write test for this function (mock an output from the API and test we're formatting it correctly)
  def toJson(parsedResponse: List[Account]): Json = {
    ???
    //Accounts(parsedResponse.map(result => Account(result._1, result._2)).toList).asJson
  }
}
