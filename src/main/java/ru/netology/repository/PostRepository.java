package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostRepository {
    private final List<Post> postsList = new CopyOnWriteArrayList<>();
    private final AtomicInteger ID = new AtomicInteger(0);

    public List<Post> all() {
        postsList.forEach(System.out::println);

        return postsList;
    }

    public Optional<Post> getById(long id) {
        Optional<Post> currentPost = postsList.stream().filter(x -> x.getId() == id).findFirst();

        return currentPost;
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
           return addPost(post);
        }

        postsList.stream().filter(x -> x.getId() == post.getId())
                .findFirst().get().setContent(post.getContent());

        return post;
    }

    private Post addPost(Post post) {
        ID.getAndIncrement();
        post.setId(ID.get());
        postsList.add(post);

        return post;
    }

    public boolean removeById(long id) {
        Optional<Post> post = postsList.stream().filter(x -> x.getId() == id).findFirst();

        post.ifPresent(postsList::remove);

        if (post.isPresent()) {
            postsList.remove(post);
            return true;
        }

        return false;
    }
}
