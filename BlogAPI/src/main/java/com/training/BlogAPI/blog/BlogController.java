package com.training.BlogAPI.blog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {
    private final BlogRepository blogRepository;
    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @PostMapping("/post-blog")
    public ResponseEntity<?> createBlog(@RequestBody Blog blog) {
        try {
            Blog savedBlog = blogRepository.save(blog);
            return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<Blog> getBlogById(@PathVariable("blogId") Long blogId) {
        Optional<Blog> blog = blogRepository.findById(blogId);
        if (blog.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(blog.get());
    }

    @GetMapping("/all-blogs")
    public ResponseEntity<List<Blog>> get(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                          @RequestParam(name = "page", defaultValue = "1000") Integer size) {
        return ResponseEntity.ok(blogRepository.findAll());
    }

    @PutMapping("/update-blog/{blogId}")
    public ResponseEntity<?> updateBlog(@PathVariable("blogId") Long blogId, @RequestBody Blog blogDetails) {
        Optional<Blog> blogToUpdateOptional = blogRepository.findById(blogId);
        if (blogToUpdateOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Blog blogToUpdate = blogToUpdateOptional.get();

        blogToUpdate.setTitle(blogDetails.getTitle());
        blogToUpdate.setDescription(blogDetails.getDescription());

        try {
            Blog updatedBlog = blogRepository.save(blogToUpdate);
            return ResponseEntity.ok(updatedBlog);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-blog/{blogId}")
    public ResponseEntity<?> deleteBlog(@PathVariable("blogId") Long blogId) {
        Optional<Blog> blogToDeleteOptional = blogRepository.findById(blogId);
        if (blogToDeleteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        blogRepository.deleteById(blogId);
        return ResponseEntity.ok(blogToDeleteOptional.get());
    }
}
