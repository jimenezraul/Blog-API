package com.raul.blogapi.service.ServiceImpl;

import com.raul.blogapi.dto.CreatePostDTO;
import com.raul.blogapi.dto.PostDTO;
import com.raul.blogapi.error.UserNotFoundException;
import com.raul.blogapi.model.Post;
import com.raul.blogapi.model.Tag;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.PostRepository;
import com.raul.blogapi.repository.TagRepository;
import com.raul.blogapi.repository.UserRepository;
import com.raul.blogapi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    TagRepository tagRepository;

    @Override
    public List<PostDTO> getLatestPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> postsPage = postRepository.findAll(pageRequest);
        return postsPage.getContent().stream().map(PostDTO::new).collect(Collectors.toList());
    }

    @Override
    public PostDTO createPost(@Valid CreatePostDTO post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        User loggedUser = userRepository.getById(user.getId());

        Post postModel = new Post();
        postModel.setId(null);
        postModel.setTitle(post.getTitle());
        postModel.setContent(post.getContent());
        postModel.setCreatedAt();
        postModel.setUpdatedAt();
        postModel.setUser(loggedUser);

        // Convert the array of tag strings into Tag entities
        List<Tag> tags = new ArrayList<>();
        for (String tagString : post.getTags()) {
            Tag tag = new Tag(tagString);
            tags.add(tag);
        }
        postModel.setTags(tags);

        return new PostDTO(postRepository.save(postModel));
    }


    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Post not found"));
        return new PostDTO(post);
    }

    @Override
    public List<PostDTO> getPostByUserId(Long id, int page, int size) {
        List<Post> posts = postRepository.findAllByUserId(id, PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return posts.stream().map(PostDTO::new).collect(Collectors.toList());
    }


    @Override
    public PostDTO updatePost(Long id, PostDTO post) {
        Post postToUpdate = postRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Post not found"));
        if (post.getTitle() != null) {
            postToUpdate.setTitle(post.getTitle());
        }
        if (post.getContent() != null) {
            postToUpdate.setContent(post.getContent());
        }
        if (post.getTags() != null) {
            List<Tag> tags = new ArrayList<>();
            for (String tagString : post.getTags()) {
                Tag tag = new Tag(tagString);
                tags.add(tag);
            }
            postToUpdate.setTags(tags);
        }

        postToUpdate.setUpdatedAt();
        return new PostDTO(postRepository.save(postToUpdate));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Long getPostByUserIdCount(Long id) {
        return postRepository.countByUserId(id);
    }

    @Override
    public Long getPostCount() {
        return postRepository.count();
    }


    private Post convertToEntity(PostDTO postDto) {
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        return post;
    }

}