package com.example.codestates.todolist.controller;


import com.example.codestates.todolist.dto.TodoDto;
import com.example.codestates.todolist.entity.Todo;
import com.example.codestates.todolist.helper.StubData;
import com.example.codestates.todolist.mapper.TodoMapper;
import com.example.codestates.todolist.service.TodoService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.codestates.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.example.codestates.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        given(mapper.todoDtoPostToTodo(any(TodoDto.Post.class)))
                .willReturn(new Todo());

        given(todoService.createTodo(any(Todo.class)))
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
                                        fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("순서"),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료여부").optional()

                                )
                        )
                ));
    }

    @Test
    @DisplayName("patchTodo 테스트")
    void patchTodoTest() throws Exception {
        System.out.println("patchTodo 테스트 시작");
        //given
        long todoId = 1L;

        TodoDto.Patch patch =
                (TodoDto.Patch) StubData.MockTodo.getRequestBody(HttpMethod.PATCH);

        TodoDto.Response response = new TodoDto.Response(1L,
                "초코파이 바나나맛 뱉기",
                1,
                false);

        given(mapper.todoDtoPatchToTodo(any(TodoDto.Patch.class)))
                .willReturn(new Todo());
        given(todoService.patchTodo(any(Todo.class)))
                .willReturn(new Todo());
        given(mapper.todoToTodoDtoResponse(any(Todo.class)))
                .willReturn(response);

        Gson gson = new Gson();
        String content = gson.toJson(patch);

        //when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .patch("/v1/todos/{todoId}", todoId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.todoId").value(patch.getTodoId()))
                .andExpect(jsonPath("$.title").value(patch.getTitle()))
                .andExpect(jsonPath("$.todoOrder").value(patch.getTodoOrder()))
                .andExpect(jsonPath("$.completed").value(patch.isCompleted()))
                .andDo(document("patch-todo",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("todoId").description("할일 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("todoId").type(JsonFieldType.NUMBER).description("할일 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("할일"),
                                        fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("순서"),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료여부")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("todoId").type(JsonFieldType.NUMBER).description("할일 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("할일"),
                                        fieldWithPath("todoOrder").type(JsonFieldType.NUMBER).description("순서"),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료여부")
                                )
                        )
                ));

    }

    @Test
    @DisplayName("getTodo 테스트")
    void getTodoTest() throws Exception {
        System.out.println("getTodo 테스트 시작");
        //given
        long todoId = 1L;

    }

    @Test
    @DisplayName("getTodos 테스트")
    void getTodosTest() throws Exception {
        System.out.println("getTodos 테스트 시작");

    }

    @Test
    @DisplayName("deleteTodo 테스트")
    void deleteTodoTest() throws Exception {
        System.out.println("deleteTodo 테스트 시작");
        //given
        long todoId = 1L;

        doNothing().when(todoService).deleteTodo(any(long.class));

        //when
        ResultActions actions = mockMvc.perform(
                delete("/v1/todos/{todoId}", todoId));

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-todo",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("todoId").description("할일 식별자")
                        )));
    }
}
