package com.raul.blogapi.service.UserServiceImpl;

import com.raul.blogapi.dto.PostDto;
import com.raul.blogapi.error.UserNotFoundException;
import com.raul.blogapi.model.Post;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.PostRepository;
import com.raul.blogapi.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostDto::new).collect(Collectors.toList());
    }

    @Override
    public PostDto createPost(PostDto post) {
        Post postModel = convertToEntity(post);
        postModel.setUser(new User(post.getUserId()));
        return new PostDto(postRepository.save(postModel));
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Post not found"));
        return new PostDto(post);
    }

    @Override
    public List<PostDto> getPostByUserId(Long id) {
        List<Post> posts = postRepository.findByUserId(id);
        return posts.stream().map(PostDto::new).collect(Collectors.toList());
    }


    @Override
    public PostDto updatePost(Long id, PostDto post) {
        Post postToUpdate = postRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Post not found"));
        postToUpdate.setDescription(post.getDescription());
        return new PostDto(postRepository.save(postToUpdate));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private Post convertToEntity(PostDto postDto) {
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        return post;
    }


}