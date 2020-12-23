package example

import scala.io.Source

object Account {
  def getAccountsFromFile(file: String): List[String] = {
    val bufferedSource = Source.fromFile(file)
    val result = bufferedSource.mkString.split(",").toList
    bufferedSource.close
    result
  }
}
