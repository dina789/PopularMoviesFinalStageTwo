package com.example.dodo.popularmoviesfinal.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReviewResponse implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose

    private List<ReviewModel> results = null;
    private final static long serialVersionUID = 3998189083738353559L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ReviewResponse() {
    }

    /**
     *
     * @param id
     * @param results
     * @param page
     */
    public ReviewResponse(Integer id, Integer page, List<ReviewModel> results) {
        super();
        this.id = id;
        this.page = page;
        this.results = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<ReviewModel> getResults() {
        return results;
    }

    public void setResults(List<ReviewModel> results) {
        this.results = results;
    }

}







