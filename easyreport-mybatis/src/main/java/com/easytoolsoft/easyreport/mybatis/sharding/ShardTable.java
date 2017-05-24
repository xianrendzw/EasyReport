package com.easytoolsoft.easyreport.mybatis.sharding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tom Deng
 * @date 2017-03-25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShardTable {
    /**
     * sharding 表名称
     */
    private String name;
}
