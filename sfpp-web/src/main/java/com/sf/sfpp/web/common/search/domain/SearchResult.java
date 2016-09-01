package com.sf.sfpp.web.common.search.domain;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/18
 */
public class SearchResult {
    private String title;
    private String link;
    private String introduction;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getIntroduction() {
        return introduction;
    }


    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
