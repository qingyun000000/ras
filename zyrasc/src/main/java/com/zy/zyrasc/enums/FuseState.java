package com.zy.zyrasc.enums;

/**
 * 熔断状态
 *
 * @author wuhailong
 */
/**
 * 熔断状态 0：正常状态 1：半开状态 2：熔断状态   3：掉线状态（该状态实际不会留存，当连续5次半开状态连接失败后，就删除该客户端。当所有客户端都掉线后，列表为空，则删除服务）
 */
public enum FuseState {
    NORMAL, HALF_FUSED, FUSED, LOST
}
