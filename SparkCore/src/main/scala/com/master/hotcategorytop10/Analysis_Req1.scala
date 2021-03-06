package com.master.hotcategorytop10

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/*
 * @Author Zhao.J
 * @Date 16:35 2021/5/13
 * @Description 热门商品top10
 * 2019-07-17_95_26070e87-1ad7-49a3-8fb3-cc741facaddf_37_2019-07-17 00:00:02_手机_-1_-1_null_null_null_null_3
 *
 */

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
    val clickCountRDD = clickCategoryRDD.map( action => {
      val datas = action.split("_")
      (datas(6), 1)
    })
//    clickCountRDD.foreach(println)

    // 3. 统计品类的下单数量：（品类ID，下单数量）
    val orderCategoryRDD = rdd.filter( action => {
      val datas = action.split("_")
      datas(8) != "null"
    })
    val orderCountRDD = orderCategoryRDD.flatMap( action => {
      val datas = action.split("_")
      val cid = datas(8)
      val cids = cid.split(",")
      cids.map(id => (id,1))
    }).reduceByKey(_+_)

    // 4. 统计品类的支付数量：（品类ID，支付数量）
    val payCategoryRDD = rdd.filter( action => {
      val datas = action.split("_")
      datas(10) != "null"
    })
    val payCountRDD = payCategoryRDD.flatMap( action => {
      val datas = action.split("_")
      val pid = datas(10)
      val pids = pid.split(",")
      pids.map(id => (id,1))
    }).reduceByKey(_+_)

    // 5. 将品类进行排序，并取前10名
    // 点击数量， 下单数量， 支付数量
    val countRDD: RDD[(String, (Iterable[Int], Iterable[Int], Iterable[Int]))] = clickCountRDD.cogroup(orderCountRDD, payCountRDD)
    val analysisRDD = countRDD.mapValues{
      case (clickIter, orderIter, payIter) => {

        var clickCnt = 0
        val iter1 = clickIter.iterator
        if(iter1.hasNext){
          clickCnt = iter1.next()
        }

        var orderCnt = 0
        val iter2 = orderIter.iterator
        if(iter2.hasNext){
          orderCnt = iter2.next()
        }

        var payCnt = 0
        val iter3 = payIter.iterator
        if(iter3.hasNext){
          payCnt = iter3.next()
        }
        (clickCnt, orderCnt, payCnt)
      }
    }
    val resultRDD = analysisRDD.sortBy(_._2, false).take(10)

    // 6.输出结果
    resultRDD.foreach(println)
    sc.stop()
  }
}
