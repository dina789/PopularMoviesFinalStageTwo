package com.example.dodo.popularmoviesfinal.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 import java.io.Serializable;

public class ReviewModel implements Serializable {

@SerializedName("author")
@Expose
private String author;
@SerializedName("content")
@Expose
private String content;
@SerializedName("id")
@Expose
private String id;
@SerializedName("url")
@Expose
private String url;
private final static long serialVersionUID = 9132832046806277777L;

/**
 * No args constructor for use in serialization
 *
 */
public ReviewModel() {
        }

/**
 *
 * @param id
 * @param content
 * @param author
 * @param url
 */
public ReviewModel(String author, String content, String id, String url) {
        super();
        this.author = author;
        this.content = content;
        this.id = id;
        this.url = url;
        }

public String getAuthor() {
        return author;
        }

public void setAuthor(String author) {
        this.author = author;
        }

public String getContent() {
        return content;
        }

public void setContent(String content) {
        this.content = content;
        }

public String getId() {
        return id;
        }

public void setId(String id) {
        this.id = id;
        }

public String getUrl() {
        return url;
        }

public void setUrl(String url) {
        this.url = url;
        }

        }