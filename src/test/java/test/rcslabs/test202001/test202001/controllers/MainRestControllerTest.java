package test.rcslabs.test202001.test202001.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import test.rcslabs.test202001.test202001.model.services.DbItemServiceInterface;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class MainRestControllerTest {
    private final String JSON_STRING =  "[{\"a\":\"val1\",\"number\":100.0,\"count\":100}]";

    @Mock
    private DbItemServiceInterface dbItemService;

    @InjectMocks
    private MainRestController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void init() throws SQLException {
        Mockito.when(dbItemService.getPivotTableJsonString(null,null)).thenThrow(new IllegalArgumentException());
        Mockito.when(dbItemService.getPivotTableJsonString("error","error")).thenThrow(new SQLException());
        Mockito.when(dbItemService.getPivotTableJsonString("a","c")).thenReturn(JSON_STRING);
        controller.setDbItemService(dbItemService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getPivotTableJson() {
        try {
            mockMvc.perform(get("/"))
                    .andExpect(status().is4xxClientError());
            mockMvc.perform(get("/?row=error&col=error"))
                    .andExpect(status().is5xxServerError());

            MvcResult result = mockMvc.perform(get("/?row=a&col=c")).andReturn();
            assertEquals(200,result.getResponse().getStatus());
            assertEquals("application/json;charset=UTF-8",result.getResponse().getContentType());
            assertEquals(JSON_STRING,result.getResponse().getContentAsString());
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }

    }
}