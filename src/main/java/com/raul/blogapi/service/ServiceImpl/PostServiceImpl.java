package com.raul.blogapi.service.ServiceImpl;

import com.raul.blogapi.dto.PostDTO;
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
    public List<PostDTO> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostDTO::new).collect(Collectors.toList());
    }

    @Override
    public PostDTO createPost(PostDTO post) {
        Post postModel = convertToEntity(post);
        postModel.setUser(new User(post.getUserId()));
        return new PostDTO(postRepository.save(postModel));
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Post not found"));
        return new PostDTO(post);
    }

    @Override
    public List<PostDTO> getPostByUserId(Long id) {
        List<Post> posts = postRepository.findByUserId(id);
        return posts.stream().map(PostDTO::new).collect(Collectors.toList());
    }


    @Override
    public PostDTO updatePost(Long id, PostDTO post) {
        Post postToUpdate = postRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Post not found"));
        postToUpdate.setDescription(post.getDescription());
        return new PostDTO(postRepository.save(postToUpdate));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private Post convertToEntity(PostDTO postDto) {
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        return post;
    }


}