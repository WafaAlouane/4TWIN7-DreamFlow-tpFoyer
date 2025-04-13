package tn.esprit.tpfoyer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer.control.FoyerRestController;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FoyerRestControllerTest {

    @Mock
    private IFoyerService foyerService;

    @InjectMocks
    private FoyerRestController foyerRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(foyerRestController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetFoyers() throws Exception {
        // Arrange
        List<Foyer> foyers = Arrays.asList(
                new Foyer(1L, "Foyer 1", 100, null, null),
                new Foyer(2L, "Foyer 2", 200, null, null)
        );
        when(foyerService.retrieveAllFoyers()).thenReturn(foyers);

        // Act & Assert
        mockMvc.perform(get("/foyer/retrieve-all-foyers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nomFoyer", is("Foyer 1")))
                .andExpect(jsonPath("$[1].nomFoyer", is("Foyer 2")));

        verify(foyerService, times(1)).retrieveAllFoyers();
    }

    @Test
    void testRetrieveFoyer() throws Exception {
        // Arrange
        Foyer foyer = new Foyer(1L, "Foyer 1", 100, null, null);
        when(foyerService.retrieveFoyer(1L)).thenReturn(foyer);

        // Act & Assert
        mockMvc.perform(get("/foyer/retrieve-foyer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomFoyer", is("Foyer 1")))
                .andExpect(jsonPath("$.capaciteFoyer", is(100)));

        verify(foyerService, times(1)).retrieveFoyer(1L);
    }

    @Test
    void testAddFoyer() throws Exception {
        // Arrange
        Foyer foyer = new Foyer(null, "Foyer 1", 100, null, null);
        Foyer savedFoyer = new Foyer(1L, "Foyer 1", 100, null, null);
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(savedFoyer);

        // Act & Assert
        mockMvc.perform(post("/foyer/add-foyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idFoyer", is(1)))
                .andExpect(jsonPath("$.nomFoyer", is("Foyer 1")));

        verify(foyerService, times(1)).addFoyer(any(Foyer.class));
    }

    @Test
    void testRemoveFoyer() throws Exception {
        // Arrange
        doNothing().when(foyerService).removeFoyer(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/foyer/remove-foyer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(foyerService, times(1)).removeFoyer(1L);
    }

    @Test
    void testModifyFoyer() throws Exception {
        // Arrange
        Foyer foyer = new Foyer(1L, "Updated Foyer", 150, null, null);
        when(foyerService.modifyFoyer(any(Foyer.class))).thenReturn(foyer);

        // Act & Assert
        mockMvc.perform(put("/foyer/modify-foyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomFoyer", is("Updated Foyer")))
                .andExpect(jsonPath("$.capaciteFoyer", is(150)));

        verify(foyerService, times(1)).modifyFoyer(any(Foyer.class));
    }
}