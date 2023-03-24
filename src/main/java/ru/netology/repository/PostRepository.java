package ru.netology.repository;

import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PostRepository {
    private final List<Post> postsList = new CopyOnWriteArrayList<>();
    private final AtomicInteger ID = new AtomicInteger(0);

    public List<Post> all() {
        for (Post post : postsList) {
            System.out.println("ID: " + post.getId() + ", CurrentContent:" + post.getContent());
        }

        return postsList;
    }

    public Optional<Post> getById(long id) {
        Optional<Post> currentPost = postsList.stream().filter(x -> x.getId() == id).findFirst();

        return currentPost;
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            ID.getAndIncrement();
            post.setId(ID.get());
            postsList.add(post);

            return post;
        }

        for (Post currentPost : postsList) {
            if (currentPost.getId() == post.getId()) {
                currentPost.setContent(post.getContent());
            } else {
                ID.getAndIncrement();
                System.out.println("Постов с таким ID не существует, новый ID: " + ID.get());
                post.setId(ID.get());
            }
        }

        postsList.add(post);

        return post;
    }

    public boolean removeById(long id) {
        boolean flag = false;
        for (Post post : postsList) {
            if (post.getId() == id) {
                postsList.remove(post);
                flag = true;
            }
        }

        return flag;
    }
}
