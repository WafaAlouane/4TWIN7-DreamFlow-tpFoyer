package tn.esprit.spring.kaddem.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContratServiceImplTest {

    private ContratRepository contratRepository;
    private ContratServiceImpl contratService;

    @BeforeEach
    void setUp() {
        contratRepository = mock(ContratRepository.class);
        contratService = new ContratServiceImpl();
        contratService.contratRepository = contratRepository;
    }

    @Test
    void testGetChiffreAffaireEntreDeuxDates() {
        // Arrange
        Contrat c1 = new Contrat();
        c1.setSpecialite(Specialite.IA);

        Contrat c2 = new Contrat();
        c2.setSpecialite(Specialite.CLOUD);

        when(contratRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        Date start = new Date(System.currentTimeMillis() - 60L * 24 * 60 * 60 * 1000); // 60 jours avant
        Date end = new Date();

        // Act
        float result = contratService.getChiffreAffaireEntreDeuxDates(start, end);

        // Assert
        assertTrue(result > 0); // simple check
    }
}
