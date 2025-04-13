package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContratServiceTest {

    @Test
    public void testAffectContratToEtudiant_Mock() {
        EtudiantRepository etudiantRepo = mock(EtudiantRepository.class);
        ContratRepository contratRepo = mock(ContratRepository.class);

        ContratServiceImpl service = new ContratServiceImpl();
        ReflectionTestUtils.setField(service, "etudiantRepository", etudiantRepo);
        ReflectionTestUtils.setField(service, "contratRepository", contratRepo);

        Etudiant e = new Etudiant();
        e.setNomE("Ali");
        e.setPrenomE("Ben Ali");

        when(etudiantRepo.findByNomEAndPrenomE("Ali", "Ben Ali")).thenReturn(e);

        Contrat c = new Contrat();
        when(contratRepo.findByIdContrat(1)).thenReturn(c);

        Contrat result = service.affectContratToEtudiant(1, "Ali", "Ben Ali");

        assertNotNull(result.getEtudiant());
        assertEquals("Ali", result.getEtudiant().getNomE());
    }
}
