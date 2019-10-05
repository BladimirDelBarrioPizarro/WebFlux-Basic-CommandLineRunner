package com.reactor.flux;

import java.util.List;

public class Comments {
    private List<String> comments;

    public Comments(){}

    public Comments(List<String> comments) {
        this.comments = comments;
    }



    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "comments=" + comments +
                '}';
    }
}
