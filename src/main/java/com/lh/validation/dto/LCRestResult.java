package com.lh.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liguowang on 2019/12/9.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LCRestResult<T> {
    private Integer resultCode;
    private String message;
    private T data;
}
