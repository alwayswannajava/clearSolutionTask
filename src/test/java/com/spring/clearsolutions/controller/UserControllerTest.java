package com.spring.clearsolutions.controller;
import com.spring.clearsolutions.ClearsolutionsApplication;
import com.spring.clearsolutions.domain.User;
import com.spring.clearsolutions.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ClearsolutionsApplication.class
)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateUser() throws Exception {
        int age = 22;
        String email = "mykhailoIvanov12@test.com";
        String firstName = "Mykhailo";
        String lastName = "Ivanov";

        String body = """
                {
                 "age": %d,
                 "email": "%s",
                 "firstName": "%s",
                 "lastName": "%s",
                 "birthDate": "2010-05-02"
                }
                """.formatted(age, email, firstName, lastName);

        MvcResult mvcResult = mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isCreated())
                .andReturn();

        String expectedResponse = "{\"" + "message\"" + ":" + "\"User was added successfully\"}";
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void testCreateUserValidation() throws Exception {
        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteUser() throws Exception {
        userRepository.saveUser(new User());
        MvcResult mvcResult = mvc.perform(delete("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponse = "{\"" + "message\"" + ":" + "\"User with id 1 deleted successfully\"}";
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testDeleteDoesntExistUser() throws Exception {
        MvcResult mvcResult = mvc.perform(delete("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn();

        String expectedResponse = "User with id 1 does not exist";
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testUpdateUser() throws Exception {
        userRepository.saveUser(new User());
        int age = 30;
        String email = "mykhailoIvanov12@test.com";
        String firstName = "Mykhailo";
        String lastName = "Ivanov";

        String body = """
                {
                 "age": %d,
                 "email": "%s",
                 "firstName": "%s",
                 "lastName": "%s",
                 "birthDate": "2010-05-02"
                }
                """.formatted(age, email, firstName, lastName);

        MvcResult mvcResult = mvc.perform(patch("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponse = "{\"" + "message\"" + ":" + "\"User with id: 1 was updated successfully\"}";
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testUpdateDoesntExistUser() throws Exception {
        int age = 30;
        String email = "mykhailoIvanov12@test.com";
        String firstName = "Mykhailo";
        String lastName = "Ivanov";

        String body = """
                {
                 "age": %d,
                 "email": "%s",
                 "firstName": "%s",
                 "lastName": "%s",
                 "birthDate": "2010-05-02"
                }
                """.formatted(age, email, firstName, lastName);

        MvcResult mvcResult = mvc.perform(patch("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isNotFound())
                .andReturn();

        String expectedResponse = "User with id 1 does not exist";
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testSearchByBirthDate() throws Exception {
        String body = """
                {
                 "fromDate": "1998-05-21",
                 "toDate": "2020-05-21"
                }
                """;
        MvcResult mvcResult = mvc.perform(post("/api/users/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponse = "{\"" + "searchUsers\"" + ":" + "[]}";
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testSeachByBirthDateWhenFromDateLessThanToDate() throws Exception {
        String body = """
                {
                 "fromDate": "2020-05-21",
                 "toDate": "1998-05-21"
                }
                """;
        MvcResult mvcResult = mvc.perform(post("/api/users/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        String expectedResponse = "Date from is less than date to";
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }


}