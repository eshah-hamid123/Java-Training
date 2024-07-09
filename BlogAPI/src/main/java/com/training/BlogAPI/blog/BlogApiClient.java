package com.training.BlogAPI.blog;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BlogApiClient {
    private final RestTemplate restTemplate;

    public BlogApiClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Cacheable(cacheNames = "blog")
    public List<Blog> findBlogs(String search) {
        String result = restTemplate
                .getForEntity("https://public-api.wordpress.com/wp/v2/sites/en.blog.wordpress.com/posts/61944", String.class).getBody();
        Blog blog = new Blog();
        blog.setDescription(result);
        return List.of(blog);
    }

}
