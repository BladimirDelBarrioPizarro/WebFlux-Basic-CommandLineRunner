package com.reactor.flux;

public class UserComments {
    private User user;
    private Comments comments;

    public UserComments(User user, Comments comments) {
        this.user = user;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "UserComments{" +
                "user=" + user +
                ", comments=" + comments +
                '}';
    }
}
