package com.liumm.hbase.endpiont;

import com.liumm.base.HbaseBase;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.ipc.CoprocessorRpcChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 协处理器终端
 *
 * @Author liumm
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Date 2018-06-30 18:18
 */
public class HbaseSingleRegionClient {

    /**
     * @param table
     * @param parentIds
     * @method callCoprocessor
     * @description 查询多个parentIds
     * @date: 18/6/30 19:09
     * @author: liumm
     */
    public Map<String, Long> callCoprocessor(Table table, String... parentIds) throws Exception {
        Map<String, Long> map = new HashMap<>();
        for (String parentId : parentIds) {
            CoprocessorRpcChannel channel = table.coprocessorService((parentId + "|").getBytes());
            HbaseProtocol.C503HbaseQueryService.BlockingInterface service = HbaseProtocol.C503HbaseQueryService.newBlockingStub(channel);
            //传参数parentId,后续的业务也可以从这里入手,丰富业务
            HbaseProtocol.C503HbaseRequest request = HbaseProtocol.C503HbaseRequest.newBuilder().setJsonStr(parentId).build();
            HbaseProtocol.C503HbaseResponse result = service.query(null, request);
            map.put(parentId, Long.parseLong(result.getResult()));
        }
        return map;
    }
}
