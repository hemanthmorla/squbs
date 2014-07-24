package org.squbs.unicomplex

import com.typesafe.config.ConfigFactory
import org.scalatest.{FunSpecLike, Matchers}

class ConfigUtilSpec extends FunSpecLike with Matchers {

  val testConfig =
    """
      |testConfig {
      |  str = foo
      |  str-list = [ foo, bar ]
      |  int-num = 10
      |  bool-val = true
      |  conf = {
      |    bool-val2 = false
      |    num2 = 20
      |  }
      |  conf-list = [
      |    {
      |      bool-val3 = true
      |      int-val3 = 30
      |    }
      |    {
      |      bool val4 = false
      |      int-val4 = 40
      |    }
      |  ]
      |}
    """.stripMargin

  val config = ConfigFactory.parseString(testConfig)

  import ConfigUtil._

  describe ("ConfigUtil") {

    it ("should read some existing string config") {
      config.getOptionalString("testConfig.str") should be (Some("foo"))
    }

    it ("should get none for non-existing string config") {
      config.getOptionalString("str") should be (None)
    }

    it ("should read some existing string list config") {
      config.getOptionalStringList("testConfig.str-list") should be (Some(Seq("foo", "bar")))
    }

    it ("should get none for non-existing string list config") {
      config.getOptionalStringList("str-list") should be (None)
    }

    it ("should read some existing int config") {
      config.getOptionalInt("testConfig.int-num") should be (Some(10))
    }

    it ("should get none for non-existing int config") {
      config.getOptionalInt("int-num") should be (None)
    }

    it ("should read some existing boolean config") {
      config.getOptionalBoolean("testConfig.bool-val") should be (Some(true))
    }

    it ("should get none for non-existing boolean config") {
      config.getOptionalBoolean("bool-val") should be (None)
    }

    it ("should read some existing config") {
      config.getOptionalConfig("testConfig.conf") flatMap { _.getOptionalInt("num2")} should be (Some(20))
    }

    it ("should get none for non-existing config") {
      config.getOptionalConfig("conf") should be (None)
    }

    it ("should read some existing config list") {
      config.getOptionalConfigList("testConfig.conf-list").get should have size 2
    }

    it ("should get none for non-existing config list") {
      config.getOptionalConfigList("conf-list") should be (None)
    }

    it ("should get provide at least one IPv$ address for any host") {
      ipv4 should fullyMatch regex """\d+\.\d+\.\d+\.\d+"""
      println(ipv4)
    }
  }
}
