package com.training.BlogAPI.blog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {
    private final BlogService blogService;


    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PreAuthorize("hasAnyAuthority('admin', 'author')")
    @PostMapping("/post-blog")
    public ResponseEntity<?> createBlog(@RequestBody Blog blog) {
        try {
            Blog savedBlog = blogService.createBlog(blog);
            return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<Blog> getBlogById(@PathVariable("blogId") Long blogId) {
        Optional<Blog> blog = blogService.getBlogById(blogId);
        if (blog.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(blog.get());
    }

    @GetMapping("/all-blogs")
    public ResponseEntity<List<Blog>> getAllBlogs(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", defaultValue = "1000") Integer size) {

        List<Blog> blogs = blogService.getAllBlogs(page, size);
        return ResponseEntity.ok(blogs);
    }

    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    @PutMapping("/update-blog/{blogId}")
    public ResponseEntity<?> updateBlog(@PathVariable("blogId") Long blogId, @RequestBody Blog blogDetails) {
        try {
            Blog updatedBlog = blogService.updateBlog(blogId, blogDetails);
            return ResponseEntity.ok(updatedBlog);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/delete-blog/{blogId}")
    public ResponseEntity<?> deleteBlog(@PathVariable("blogId") Long blogId) {
        try {
            Optional<Blog> deletedBlog = blogService.deleteBlog(blogId);
            return ResponseEntity.ok(deletedBlog);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
