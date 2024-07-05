package com.training.BlogAPI.blog;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Optional<Blog> getBlogById(Long blogId) {
        return blogRepository.findById(blogId);
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog updateBlog(Long blogId, Blog blogDetails) {
        Optional<Blog> blogToUpdateOptional = blogRepository.findById(blogId);
        if (blogToUpdateOptional.isEmpty()) {
            throw new RuntimeException("Blog not found");
        }

        Blog blogToUpdate = blogToUpdateOptional.get();
        blogToUpdate.setTitle(blogDetails.getTitle());
        blogToUpdate.setDescription(blogDetails.getDescription());

        return blogRepository.save(blogToUpdate);
    }

    public void deleteBlog(Long blogId) {
        blogRepository.deleteById(blogId);
    }
}
