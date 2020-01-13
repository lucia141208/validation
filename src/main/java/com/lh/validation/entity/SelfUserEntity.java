package com.lh.validation.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author liuhh    @Date 2020/1/9 17:27
 */
@Data
@Builder
public class SelfUserEntity {
    private Integer userId;
    private String username;
    private List authorities;
}
