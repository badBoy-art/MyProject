package com.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-05-06
 * @Description
 */
@Mapper
public interface OrderMapper {

    @Mapping(source = "source", target = "sourceType")
    OrderQueryParam entity2queryParam(Order order);

}
