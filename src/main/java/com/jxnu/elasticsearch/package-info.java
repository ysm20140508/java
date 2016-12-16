/**
 *  elasticsearch业务使用的要点
 *  1:业务数据同步  把数据库的业务数据同步到elasticsearch
 *    解决办法: 同步系统监听数据库业务表的更新字段,实时更新新增到elasticsearch中,主要业务数据的软删除
 *
 *  2：精确查询 特殊字符(￥%……&*（）！@#)导致的不匹配
 *    解决办法: match_phase
 *
 *  3：大小写不匹配
 *    解决办法: 写入elassearch时，全部转成小写
 *
 *
 * @author shoumiao_yao
 * @date 2016-12-16
 */
package com.jxnu.elasticsearch;