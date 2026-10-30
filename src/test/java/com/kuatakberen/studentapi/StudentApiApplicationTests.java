package com.kuatakberen.studentapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
class StudentApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository repository;

    private String json(String name, String email, int age) {
        return "{\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"age\":" + age + "}";
    }

    private Student saved(String email) {
        return repository.save(new Student("Kuat Akberen", email, 20));
    }

    @Test
    void contextLoads() {
        System.out.println("=== Kuat Akberen - Context load test passed ===");
    }

    @Test
    void infoShowsStudentName() throws Exception {
        mockMvc.perform(get("/api/info"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.student").value("Kuat Akberen"));
    }

    @Test
    void createsStudent() throws Exception {
        mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                .content(json("Kuat Akberen", "create@kuat.test", 20)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value("Kuat Akberen"));
    }

    @Test
    void rejectsInvalidStudent() throws Exception {
        mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                .content(json("", "not-an-email", 20)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error", containsString("email")));
    }

    @Test
    void rejectsDuplicateEmail() throws Exception {
        saved("duplicate@kuat.test");
        mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                .content(json("Kuat Akberen", "duplicate@kuat.test", 20)))
            .andExpect(status().isConflict());
    }

    @Test
    void readsAllAndOne() throws Exception {
        Student student = saved("read@kuat.test");
        mockMvc.perform(get("/api/students")).andExpect(status().isOk());
        mockMvc.perform(get("/api/students/" + student.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("read@kuat.test"));
    }

    @Test
    void updatesStudent() throws Exception {
        Student student = saved("update@kuat.test");
        mockMvc.perform(put("/api/students/" + student.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(json("Akberen Kuat", "update@kuat.test", 21)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Akberen Kuat"))
            .andExpect(jsonPath("$.age").value(21));
    }

    @Test
    void deletesStudent() throws Exception {
        Student student = saved("delete@kuat.test");
        mockMvc.perform(delete("/api/students/" + student.getId())).andExpect(status().isOk());
        mockMvc.perform(get("/api/students/" + student.getId())).andExpect(status().isNotFound());
    }

    @Test
    void nonexistentStudentReturns404() throws Exception {
        mockMvc.perform(get("/api/students/999999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error", containsString("999999")));
        mockMvc.perform(delete("/api/students/999999")).andExpect(status().isNotFound());
    }
}
