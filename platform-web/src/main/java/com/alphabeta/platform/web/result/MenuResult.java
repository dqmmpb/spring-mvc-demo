package com.alphabeta.platform.web.result;

import com.alphabeta.platform.core.domain.BaseResult;
import com.alphabeta.platform.web.result.model.MenuModel;

import java.util.List;

/**
 * 模块名
 *
 * @author deng.qiming
 * @date 2016-12-26 19:10
 */
public class MenuResult extends BaseResult {

    private List<MenuModel> result;

    @Override
    public List<MenuModel> getResult() {
        return result;
    }

    public void setResult(List<MenuModel> result) {
        this.result = result;
    }
}
