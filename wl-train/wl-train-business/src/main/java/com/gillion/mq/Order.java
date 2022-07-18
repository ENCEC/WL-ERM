package com.gillion.mq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wengms
 * @date 2020/2/6 2:27 下午
 * @email wengms@gillion.com.cn
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private String orderId;
    private String orderNo;
    private String createUser;
    private String type;

}
