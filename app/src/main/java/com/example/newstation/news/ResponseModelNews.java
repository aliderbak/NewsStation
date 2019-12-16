package com.example.newstation.news;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModelNews {
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("articles")
    private List<ArticleNews> articles = null;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getTotalResults() {
        return totalResults;
    }
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
    public List<ArticleNews> getArticles() {
        return articles;
    }
    public void setArticles(List<ArticleNews> articles) {
        this.articles = articles;
    }
}
