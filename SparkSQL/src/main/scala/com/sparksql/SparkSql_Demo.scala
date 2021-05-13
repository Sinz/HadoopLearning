package com.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSql_Demo {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql_Demo")

    val spark: SparkSession = new SparkSession(sparkConf)

    val frame = spark.read.json("in/user.json")

    frame.show()

    spark.stop()
  }
}
