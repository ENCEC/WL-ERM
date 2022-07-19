package com.share.message.dto;

import lombok.*;


import javax.validation.constraints.NotEmpty;
/**
 * @Description  MsgMarcoDto
 * @Author nily
 * @Date 2020/11/25
 * @Time 9:24 上午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MsgMarcoDto {
    @NotEmpty
    private Long msgMarcoId;

    private String businessSystemId;

    @NotEmpty
    private String marcoNameCn;

    @NotEmpty
    private String marcoNameEn;

    private Boolean status;

    @NotEmpty
    private String fieldName;

}
