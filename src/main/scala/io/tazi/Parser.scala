package io.tazi

import com.typesafe.config.{ConfigFactory, ConfigRenderOptions}

import scala.util.parsing.json._
/**
  * Created by guemues on 17/06/16.
  */
class CEFParser {

  // Keys are predefined
  val KEYS = List(
    "CEFVersion",
    "DeviceVendor",
    "DeviceProduct",
    "DeviceVersion",
    "SignatureID",
    "Name",
    "Severity"
  )

  def parse(string: String): String ={
    var options = ConfigRenderOptions.defaults()
    options = options.setOriginComments(false)
    options = options.setComments(false)

    val allEntries = scala.collection.mutable.Map[String, String]()
    val tokens = string.split("(?<!\\\\)\\|")

    if (tokens.length < 8) {
      throw new Exception()
    }
    val extension = tokens(7)
    val cefData = this.toConfig(tokens, extension)
    val jsonOut = JSON.parseFull(cefData.root().render(options)).getOrElse(0)

    jsonOut match {
      case map_ : Map[String, String] =>
        allEntries ++= map_
      case _ =>
        throw new Exception()
    }
    JSONObject(allEntries.toMap).toString()
  }

  private def toConfig(tokens: Array[String], extension: String): com.typesafe.config.Config ={
    val regexComma = """\s+(?=([^"]*"[^"]*")*[^"]*$)""".r
    val preConfig = ConfigFactory.parseString("{" + KEYS.zip(tokens).map( x => "\"" + x._1 + "\" : \"" + x._2 + "\"").mkString(", ") + "}")
    ConfigFactory.parseString(regexComma.replaceAllIn(extension, ", ")).withFallback(preConfig)
  }



}
