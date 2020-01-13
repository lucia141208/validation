package com.lh.validation.entity;

import com.lh.validation.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationPoint extends BaseEntity {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;

    private Integer gpsDate;

    private Integer speed;

    private Integer longitude;

    private Integer latitude;

    private Integer direction;

    private String dynamicLoad;

    private String gpsDateStr;

}