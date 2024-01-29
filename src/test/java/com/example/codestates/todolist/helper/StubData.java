package com.example.codestates.todolist.helper;

import com.example.codestates.todolist.dto.TodoDto;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class StubData {

    private static Map<HttpMethod, Object> stubRequestBody;

    static {
        stubRequestBody = new HashMap<>();
        stubRequestBody.put(HttpMethod.POST, new TodoDto.Post("초코파이 바나나맛 먹기", 1, false));
        stubRequestBody.put(HttpMethod.PATCH, TodoDto.Patch.builder().todoId(1L).title("초코파이 바나나맛 뱉기").todoOrder(1).build());
    }

    public static class MockTodo {
        public static Object getRequestBody(HttpMethod method) {

            return stubRequestBody.get(method);
        }

        public static TodoDto.Response getSingleResponseBody() {
            return new TodoDto.Response(1L,
                    "초코파이 바나나맛 먹기",
                    1, false);
        }
    }
}
