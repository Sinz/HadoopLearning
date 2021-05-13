package com.c503.sparksql

import com.c503.utils.SparkSqlUtils
import org.apache.spark.sql.SparkSession

/**
  * 描述 简单描述方法的作用
  *
  * @author liumm
  * @since 2018-09-16 15:11
  */
object Spark_SQL_1 {

  def main(args: Array[String]): Unit = {

    SparkSqlUtils.offLogger()

    val spark = SparkSqlUtils.newSparkSession("spark_sql_1")

    val df = spark.read.json(SparkSqlUtils.getPathByName("person.json"))

    df.show()

    //聚合，排序求和，平均
    df.createOrReplaceTempView("person")
    val result_normal = spark.sql("" +
      "select distinct(a.sex), a.avg_age, a.min_age, a.max_age, a.max_subtract_min_age, a.count_name, b.count_name_than_30 from " +
      "(select " +
      "avg(age) as avg_age, " +
      "min(age) as min_age, " +
      "max(age) as max_age, " +
      "max(age) - min(age) as max_subtract_min_age, " +
      "count(name) as count_name, " +
      "sex " +
      "from person " +
      "group by sex) as a, " +
      "(select " +
      "count(name) as count_name_than_30 " +
      "from person " +
      "where age >= 30 " +
      "group by sex) b "
    )
    result_normal.show()

    val result_than_30 = spark.sql("" +
      "select " +
      "count(name) as count_name_than_30 " +
      "from person " +
      "where age >= 30 " +
      "group by sex"
    )
    result_than_30.show()


  }


  def baseHandler(): Unit = {
    val spark = SparkSqlUtils.newSparkSession("spark_sql_1")

    val df = spark.read.json(SparkSqlUtils.getPathByName("person.json"))

    //展示数据
    df.show()

    //查看schema
    df.printSchema()

    // 选择多列
    df.select(df("name"), df("age") + 1).show()

    // 条件过滤
    df.filter(df("age") > 30).show()

    // 分组聚合
    df.groupBy(df("age")).count().show()

    // 排序
    df.sort(df("name").desc).show()

    //多列排序
    df.sort(df("name").desc, df("age").asc).show()

    //对列进行重命名
    df.select(df("name").as("username"), df("age")).show()
  }

}
