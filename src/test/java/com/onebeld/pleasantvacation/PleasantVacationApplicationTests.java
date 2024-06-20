package com.onebeld.pleasantvacation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Класс для тестирования приложения
 */
@SpringBootTest
@AutoConfigureMockMvc
class PleasantVacationApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Получает путевку из API и печатает ее содержимое.
     *
     * @throws Exception Если во время запроса API произошла ошибка
     */
    @Test
    void getTripFromApi() throws Exception {
        // Отправляется get-запрос на получение путевки
        // Ожидается, что код состояния будет 200 (andExpect(status().isOk()))
        // Результат запроса должен возвращается (andReturn())
        MvcResult result = mockMvc.perform(get("/api/tours/3"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }
}
