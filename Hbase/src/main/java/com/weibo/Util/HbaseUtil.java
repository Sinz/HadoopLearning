package com.weibo.Util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;

/*
 * @Author Zhao.J
 * @Date 11:43 2021/4/23
 * @Description 普通工具类
 *  创建命名空间
 */
public class HbaseUtil {

    // 连接Hbase
    private static Connection connection = null;
    private static Admin admin = null;
    private static Configuration conf = null;

    static{
        conf = HBaseConfiguration.create();

    }
}
