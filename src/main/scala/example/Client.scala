package example

import com.amazonaws.auth.AWSCredentialsProviderChain
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.ec2.{AmazonEC2Async, AmazonEC2AsyncClientBuilder}


// TODO: https://github.com/guardian/security-hq/blob/dde136c9c461b641df9aa9efaf956c03ed2b5c90/hq/app/aws/AWS.scala#L22

object Client {
  val defaultRegion: Regions = Regions.EU_WEST_1
  private def credentialsProviderChain(account: String): AWSCredentialsProviderChain = {
    new AWSCredentialsProviderChain(
      new ProfileCredentialsProvider(account)
    )
  }

  def createClient(account: String): AmazonEC2Async = {
    AmazonEC2AsyncClientBuilder.standard()
      .withCredentials(credentialsProviderChain(account))
      .withRegion(defaultRegion)
      .build()
  }
}
