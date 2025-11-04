package com.codegym.teluscospringsecurity.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.codegym.teluscospringsecurity.model.Student;
import com.codegym.teluscospringsecurity.service.impl.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StudentControllerIntegrationTest {
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void whenNoStudents_thenReturnNoContent() throws Exception {
        // Giả lập service trả về danh sách rỗng
        when(studentService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenStudentsExist_thenReturnOkAndJsonArray() throws Exception {
        // Giả lập service trả về danh sách có dữ liệu
        Student student = new Student();
        student.setId(1L);
        student.setName("Nguyen Van A");
        student.setMarks(70);

        when(studentService.findAll()).thenReturn(List.of(student));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Nguyen Van A"))
                .andExpect(jsonPath("$[0].marks").value(70));
    }

    @Test
    void whenValidStudentPosted_thenReturnCreatedAndSavedStudent() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        // Given
        Student studentToSave = new Student();
        studentToSave.setName("Nguyen Van A");
        studentToSave.setMarks(70);

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("Nguyen Van A");
        savedStudent.setMarks(70);

        // Khi gọi save → giả lập service trả về student đã có id
        when(studentService.save(any(Student.class))).thenReturn(savedStudent);

        // Thực hiện POST
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentToSave)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Nguyen Van A"))
                .andExpect(jsonPath("$.marks").value(70));
    }
}
