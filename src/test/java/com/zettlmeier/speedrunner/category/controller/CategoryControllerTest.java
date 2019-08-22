package com.zettlmeier.speedrunner.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.category.service.CategoryService;
import com.zettlmeier.speedrunner.entities.service.RestResponseEntityBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @MockBean
    private RestResponseEntityBuilder restResponseEntityBuilder;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper testMapper = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        Mockito.when(restResponseEntityBuilder.buildGETResponse(Mockito.any())).thenCallRealMethod();
    }

    @Test
    public void getCategoriesTest() throws Exception {
        var categories = new ArrayList<Category>();
        var category = new Category();
        category.setName("Any%");
        categories.add(category);
        Mockito.when(categoryService.getCategories()).thenReturn(categories);

        this.mockMvc.perform(get("/category"))
                .andDo(result -> {
                    var json = testMapper.readTree(result.getResponse().getContentAsByteArray());
                    assertThat(json.get(0).at("/name").asText()).isEqualTo("Any%");
                });

        Mockito.verify(categoryService, times(1)).getCategories();
    }
}
