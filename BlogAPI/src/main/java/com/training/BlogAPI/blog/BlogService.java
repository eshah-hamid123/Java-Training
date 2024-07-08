package com.training.BlogAPI.blog;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public List<Blog> findAll(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > 1000) {
            size = 1000;
        }
        return blogRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Optional<Blog> getBlogById(Long blogId) {
        return blogRepository.findById(blogId);
    }

    public List<Blog> getAllBlogs(Integer page, Integer size) {
        return findAll(page, size);
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

    public Optional<Blog> deleteBlog(Long blogId) {
        Optional<Blog> deletedBlog = getBlogById(blogId);
         blogRepository.deleteById(blogId);
        return deletedBlog;
    }
}
