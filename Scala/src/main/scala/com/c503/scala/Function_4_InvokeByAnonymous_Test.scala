package com.c503.scala

/**
  * 描述 匿名函数
  *
  * @author liumm
  * @since 2018-07-07 15:22
  */
object Function_4_InvokeByAnonymous_Test {

  var aaa = (x: String) => x * 2

  var inc = (x: Int) => x + 1

  var dec = (x: Int) => x - 1

  var x = inc(7) - 1

  var mul = (x: Int, y: Int) => x * y

  def main(args: Array[String]): Unit = {

    println(aaa("1"))
    println(inc(1))
    println(dec(1))
    println(x)
    println(mul(1, 2))


  }

}
