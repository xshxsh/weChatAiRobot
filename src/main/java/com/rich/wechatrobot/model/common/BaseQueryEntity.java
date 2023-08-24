package com.rich.wechatrobot.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 基础分页实体类
 *
 * @author AI
 * @date 2023/2/26 21:10
 **/
@ApiModel("基础分页实体类")
public class BaseQueryEntity {

    @ApiModelProperty("查询条数")
    private Integer pageSize;

    @ApiModelProperty("查询当前页数")
    private Integer pageIndex;

    public BaseQueryEntity() {
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

}
