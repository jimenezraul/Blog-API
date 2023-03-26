package com.raul.blogapi.service.ServiceImpl;

import com.raul.blogapi.dto.PostDTO;
import com.raul.blogapi.error.UserNotFoundException;
import com.raul.blogapi.model.Post;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.PostRepository;
import com.raul.blogapi.repository.UserRepository;
import com.raul.blogapi.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PostDTO> getLatestPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> postsPage = postRepository.findAll(pageRequest);
        return postsPage.getContent().stream().map(PostDTO::new).collect(Collectors.toList());
    }

    @Override
    public PostDTO createPost(PostDTO post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        User loggedUser = userRepository.getById(user.getId());

        Post postModel = new Post();
        postModel.setId(null);
        postModel.setTitle(post.getTitle());
        postModel.setBody(post.getBody());
        postModel.setCreatedAt();
        postModel.setUpdatedAt();
        postModel.setUser(loggedUser);
        return new PostDTO(postRepository.save(postModel));
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Post not found"));
        return new PostDTO(post);
    }

    @Override
    public List<PostDTO> getPostByUserId(Long id) {
        List<Post> posts = postRepository.findAllByUserIdOrderByCreatedAtDesc(id);
        return posts.stream().map(PostDTO::new).collect(Collectors.toList());
    }


    @Override
    public PostDTO updatePost(Long id, PostDTO post) {
        Post postToUpdate = postRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Post not found"));
        postToUpdate.setBody(post.getBody());
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