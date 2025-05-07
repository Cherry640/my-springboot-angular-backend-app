package com.bezkoder.spring.jpa.h2.controller;

import com.bezkoder.spring.jpa.h2.controller.TutorialController;
import com.bezkoder.spring.jpa.h2.model.Tutorial;
import com.bezkoder.spring.jpa.h2.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TutorialControllerTest {

    @Mock
    private TutorialRepository tutorialRepository;

    @InjectMocks
    private TutorialController tutorialController;

    @Test
    public void testHome()
    {
        String response = tutorialController.home();
        assertEquals("Welcome to Spring Boot Tutorial by Chaheti!", response);
    }

    @Test
    public void testGetAllTutorialsForNull() {
        // Create mock data
        List<Tutorial> tutorials = Arrays.asList(
                new Tutorial(1L, "Tutorial 1", "Description for tutorial 1", true),
                new Tutorial(2L, "Tutorial 2", "Description for tutorial 2", false)
        );

        when(tutorialRepository.findAll()).thenReturn(tutorials);

        ResponseEntity<List<Tutorial>> response = tutorialController.getAllTutorials(null);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    @Test
    public void testGetAllTutorialsForNotNull() {
        // Create mock data
        List<Tutorial> tutorials = Arrays.asList(
                new Tutorial(1L, "Tutorial 1", "Description for tutorial 1", true),
                new Tutorial(2L, "Tutorial 2", "Description for tutorial 2", false)
        );

        when(tutorialRepository.findByTitleContainingIgnoreCase("test")).thenReturn(tutorials);

        ResponseEntity<List<Tutorial>> response = tutorialController.getAllTutorials("test");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    @Test
    public void testGetAllTutorialsForEmpty() {
        // Create mock data
        List<Tutorial> tutorials = new ArrayList<>();

        when(tutorialRepository.findByTitleContainingIgnoreCase("test")).thenReturn(tutorials);

        ResponseEntity<List<Tutorial>> response = tutorialController.getAllTutorials("test");

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        //assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetAllTutorialsForException() {
        // Create mock data
        List<Tutorial> tutorials = null;

        when(tutorialRepository.findByTitleContainingIgnoreCase("test")).thenReturn(tutorials);

        ResponseEntity<List<Tutorial>> response = tutorialController.getAllTutorials("test");

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        //assertEquals(2, response.getBody().size());
    }
    @Test
    public void testGetTutorialByIdForValid() {
        // Create mock data
        List<Tutorial> tutorials = Arrays.asList(
                new Tutorial(1L, "Tutorial 1", "Description for tutorial 1", true),
                new Tutorial(2L, "Tutorial 2", "Description for tutorial 2", false)
        );

        Optional<Tutorial> tutorialData = Optional.ofNullable(tutorials.get(0));
        when(tutorialRepository.findById(1L)).thenReturn(tutorialData);

        ResponseEntity<Tutorial> response = tutorialController.getTutorialById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
    @Test
    public void testGetTutorialByIdForInvalid() {
        // Create mock data
        List<Tutorial> tutorials = Arrays.asList(
                new Tutorial(1L, "Tutorial 1", "Description for tutorial 1", true),
                new Tutorial(2L, "Tutorial 2", "Description for tutorial 2", false)
        );

        Optional<Tutorial> tutorialData = Optional.empty();
        when(tutorialRepository.findById(1L)).thenReturn(tutorialData);

        ResponseEntity<Tutorial> response = tutorialController.getTutorialById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }


}