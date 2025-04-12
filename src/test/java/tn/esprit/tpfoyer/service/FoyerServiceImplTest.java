package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FoyerServiceImplTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllFoyers() {
        // Arrange
        List<Foyer> foyers = Arrays.asList(
                new Foyer(1L, "Foyer A", 100, null, null),
                new Foyer(2L, "Foyer B", 200, null, null)
        );
        when(foyerRepository.findAll()).thenReturn(foyers);

        // Act
        List<Foyer> result = foyerService.retrieveAllFoyers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Foyer A", result.get(0).getNomFoyer());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveFoyer() {
        // Arrange
        Foyer foyer = new Foyer(1L, "Foyer A", 100, null, null);
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Act
        Foyer result = foyerService.retrieveFoyer(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Foyer A", result.getNomFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveFoyerNotFound() {
        // Arrange
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            foyerService.retrieveFoyer(1L);
        });
        assertEquals("Foyer not found with id: 1", exception.getMessage());
    }

    @Test
    void testAddFoyer() {
        // Arrange
        Foyer foyer = new Foyer(null, "Foyer A", 100, null, null);
        Foyer savedFoyer = new Foyer(1L, "Foyer A", 100, null, null);
        when(foyerRepository.save(any(Foyer.class))).thenReturn(savedFoyer);

        // Act
        Foyer result = foyerService.addFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdFoyer());
        verify(foyerRepository, times(1)).save(any(Foyer.class));
    }

    @Test
    void testModifyFoyer() {
        // Arrange
        Foyer foyer = new Foyer(1L, "Foyer A", 100, null, null);
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        // Act
        Foyer result = foyerService.modifyFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals("Foyer A", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(any(Foyer.class));
    }

    @Test
    void testRemoveFoyer() {
        // Arrange
        when(foyerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(foyerRepository).deleteById(1L);

        // Act
        foyerService.removeFoyer(1L);

        // Assert
        verify(foyerRepository, times(1)).existsById(1L);
        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRemoveFoyerNotFound() {
        // Arrange
        when(foyerRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            foyerService.removeFoyer(1L);
        });
        assertEquals("Foyer not found with id: 1", exception.getMessage());
    }
}