package com.example.codestates.todolist.controller;


import com.example.codestates.todolist.dto.TodoDto;
import com.example.codestates.todolist.entity.Todo;
import com.example.codestates.todolist.mapper.TodoMapper;
import com.example.codestates.todolist.service.TodoService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.codestates.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.example.codestates.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private TodoService todoService;

    @MockBean
    private TodoMapper mapper;

    @Test
    @DisplayName("Post 테스트")
    void postTodoTest() throws Exception {
        System.out.println("Post 테스트 시작");
        //given
        TodoDto.Post post = new TodoDto.Post("초코파이 바나나맛 먹기", 1, false);

        Todo mockResultTodo = new Todo();
        mockResultTodo.setTodoId(1L);

        given(mapper.todoDtoPostToTodo(Mockito.any(TodoDto.Post.class)))
                .willReturn(new Todo());

        given(todoService.createTodo(Mockito.any(Todo.class)))
                .willReturn(mockResultTodo);

        String content = gson.toJson(post);
        URI uri = UriComponentsBuilder.newInstance().path("/v1/todos").build().toUri();

        //when
        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );
        //then
        actions
                .andExpect(status().isCreated())
                .andDo(document(
                        "post-todo",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("할일"),
                                        fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("순서")
                                )
                        )
                ));
    }
}
