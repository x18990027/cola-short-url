package com.cola.common.core.domain;

;
import com.github.pagehelper.Page;
import org.apache.poi.ss.formula.functions.T;

import javax.validation.Valid;
import java.util.List;

public class RequestData {


    /**
     *
     */
    private static final long serialVersionUID = 6880468436645776327L;

    private String authorization;

    @Valid
    private T param;

    private Page page;

    private int sort;

    /** 排序字段 */
    private String sortField;

    /** 多个排序参数：0,降序；1升序 */
    private List<Integer> sortList;

    /** 多个排序字段 */
    private List<String> sortFieldList;

    /**
     * @return the token
     */
    public String getAuthorization() {

        return authorization;
    }

    /**
     * @param token
     *              the token to set
     */
    public void setAuthorization(String token) {

        this.authorization = token;
    }

    /**
     * @return the param
     */
    public T getParam() {

        return param;
    }

    /**
     * @param param
     *              the param to set
     */
    public void setParam(T param) {

        this.param = param;
    }

    /**
     * @return the page
     */
    public Page getPage() {

        return page;
    }

    /**
     * @param page
     *             the page to set
     */
    public void setPage(Page page) {

        this.page = page;
    }

    /**
     * @return 返回 sort
     */
    public int getSort() {

        return sort;
    }

    /**
     * @param sort 设置 sort
     */
    public void setSort(int sort) {

        this.sort = sort;
    }

    /**
     * @return 返回 sortField
     */
    public String getSortField() {

        return sortField;
    }

    /**
     * @param sortField 设置 sortField
     */
    public void setSortField(String sortField) {

        this.sortField = sortField;
    }


    /**
     * @return 返回 sortList
     */
    public List<Integer> getSortList() {

        return sortList;
    }

    /**
     * @param sortList 设置 sortList
     */
    public void setSort(List<Integer> sortList) {

        this.sortList = sortList;
    }

    /**
     * @return 返回 sortFieldList
     */
    public List<String> getSortFieldList() {

        return sortFieldList;
    }

    /**
     * @param sortFieldList 设置 sortFieldList
     */
    public void setSortFieldList(List<String> sortFieldList) {

        this.sortFieldList = sortFieldList;
    }


}
