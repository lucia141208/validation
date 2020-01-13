package com.lh.validation.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 被继承的Mapper，一般业务Mapper继承它
 *
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
