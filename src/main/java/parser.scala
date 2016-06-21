
import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.{Files, Paths}

import com.typesafe.config.{ConfigFactory, ConfigRenderOptions}

import scala.io.Source
import scala.util.parsing.json._
/**
  * Created by guemues on 17/06/16.
  */



object parser{

  // If it needed to use key strings

  val keys = List(
    "CEFVersion",
    "DeviceVendor",
    "DeviceProduct",
    "DeviceVersion",
    "SignatureID",
    "Name",
    "Severity"
  )


  def main(args: Array[String]): Unit ={

    //File creation
    val path = Paths.get("out.json")
    if (Files.exists(path))
      Files.delete(path)

    Files.createFile(path)
    val output = new File("out.json")
    val bw = new BufferedWriter(new FileWriter(output))

    //Input
    val filename = "cef.log"

    // val tokenList = keys.mkString("|")
    val regexComma = """\s+(?=([^"]*"[^"]*")*[^"]*$)""".r

    // val pattern = "([^\"=]*)=(\"[^\"]+\"|[^\"=]*)\\s(?:.*?|$)".r

    try {
      val mutableMap = scala.collection.mutable.Map[String, String]()

      for (line <- Source.fromFile(filename).getLines()) {

        val tokens = line.split("(?<!\\\\)\\|")
        var extension = ""
        if (tokens.length == 8) {
          extension = tokens(7)
        }
        if (tokens.length < 8) {
          sys.exit(1)
        }
        var options = ConfigRenderOptions.defaults()
        options = options.setOriginComments(false)
        options = options.setComments(false)
        print()
        val preConfig = ConfigFactory.parseString("{" + keys.zip(tokens).map( x => "\"" + x._1 + "\" : \"" + x._2 + "\"").mkString(", ") + "}")
        val cefData = ConfigFactory.parseString(regexComma.replaceAllIn(extension, ", ")).withFallback(preConfig)
        val jsonOut = JSON.parseFull(cefData.root().render(options)).getOrElse(0)

        jsonOut match {
          case map_ : Map[String, String] =>
            mutableMap ++= map_
          case _ =>
            print("Error")
        }


        bw.write(JSONObject(mutableMap.toMap).toString())
      }

    } catch {
      case ex: Exception => println(ex)
    }
    bw.close()
  }

}
