package com.alphabeta.platform.core.domain;

import com.alphabeta.platform.core.common.Const;

/**
 * 返回分页参数基类
 *
 * @author deng.qiming
 * @date 2016年11月8日 上午10:49:47
 */
public class PageParam {
    private Integer pageNum;
    private Integer pageSize;

    public PageParam() {
        this.pageNum = Const.PAGE_PAGENUM_DEFAULT;
        this.pageSize = Const.PAGE_PAGESIZE_DEFAULT;
    }


    public PageParam(Integer pageNum) {
        this.pageNum = pageNum;
        this.pageSize = Const.PAGE_PAGESIZE_DEFAULT;
    }


    public PageParam(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
