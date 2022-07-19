package com.share.file.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author nilingyun
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoReturnVo<T> {
    private String resultCode;
    private String resultMsg;
    private T data;


}
