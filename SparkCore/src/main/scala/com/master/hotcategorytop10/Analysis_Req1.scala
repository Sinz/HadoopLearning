package com.master.hotcategorytop10

import org.apache.spark.{SparkConf, SparkContext}

object  Analysis_Req1 {

  /*
   * @Author Zhao.J
   * @Date 15:41 2021/5/13
   * @Description 热门品类top10
   */
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10Analysis")
    val sc = new SparkContext(sparkConf)

    // 1. 读取原始数据
    val rdd = sc.textFile("datas/user_visit_action.txt")

    // 2. 统计品类的点击数量：（品类ID，点击数量）
    val clickCategoryRDD = rdd.filter( action => {
      val datas = action.split("_")
      datas(6) != -1
    })
    val clickCountRDD = rdd.map( action => {
      val datas = action.split("_")
      (datas(6), 1)
    })

    clickCountRDD.foreach(println)


    // 3. 统计品类的下单数量：（品类ID，下单数量）
//    val orderCategoryRDD =



  }
}
