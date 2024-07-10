package com.training.BlogAPI;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BlogApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Order(1)
	@Test
	void testCreateBlog() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/blog/post-blog")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\": \"Test Blog\", \"description\": \"Test Blog description\", \"authorName\": \"Eisha\"}")
						.with(csrf())
						.with(user("admin").authorities(() -> "admin")))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Blog"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Blog description"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.authorName").value("Eisha"));

	}

	@Order(2)
	@Test
	void testGetBlogById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/blog/1")
						.with(csrf())
						.with(user("user").authorities(() -> "user")))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Blog"));
	}

	@Test
	public void testNBlogGetNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/blog/2")
						.with(user("admin").authorities(() -> "admin")))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetAllBlogs() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/blog/all-blogs")
						.with(csrf())
						.with(user("user").authorities(() -> "user")))
				.andExpect(status().isOk());
	}

	@Order(3)
	@Test
	void testUpdateBlog() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/blog/update-blog/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\": \"Updated Blog\", \"description\": \"Test Blog description updated\", \"authorName\": \"Sania\"}")
						.with(csrf())
						.with(user("admin").authorities(() -> "admin")))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Blog"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Blog description updated"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.authorName").value("Sania"));
	}

	@Order(4)
	@Test
	void testDeleteBlog() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/blog/delete-blog/1")
						.with(csrf())
						.with(user("admin").authorities(() -> "admin")))
				.andExpect(status().isOk());
	}

}
