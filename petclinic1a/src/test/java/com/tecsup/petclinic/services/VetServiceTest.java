package com.tecsup.petclinic.services;


import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VetServiceTest {

    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VetService vetService;

    private Vet sampleVet;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        sampleVet = new Vet("James", "Carter", "james.carter@petclinic.com", "6085551234", true);
        sampleVet.setId(1L);
    }

    @Test
    void testCreateVet() {
        when(vetRepository.save(any(Vet.class))).thenReturn(sampleVet);

        Vet created = vetService.create(new Vet("James", "Carter", "james.carter@petclinic.com", "6085551234", true));

        assertNotNull(created);
        assertEquals("James", created.getFirstName());
        assertEquals("Carter", created.getLastName());
        verify(vetRepository, times(1)).save(any(Vet.class));
    }

    @Test
    void testUpdateVet() {
        Vet updated = new Vet("Helen", "Leary", "helen.leary@petclinic.com", "6085552345", true);
        updated.setId(1L);
        when(vetRepository.save(any(Vet.class))).thenReturn(updated);

        Vet result = vetService.update(updated);

        assertEquals("Helen", result.getFirstName());
        assertEquals("Leary", result.getLastName());
        verify(vetRepository, times(1)).save(any(Vet.class));
    }

    @Test
    void testFindVetById() {
        when(vetRepository.findById(1L)).thenReturn(Optional.of(sampleVet));

        Optional<Vet> result = vetService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("James", result.get().getFirstName());
        verify(vetRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllVets() {
        List<Vet> vets = Arrays.asList(sampleVet,
                new Vet("Helen", "Leary", "helen.leary@petclinic.com", "6085552345", true));
        when(vetRepository.findAll()).thenReturn(vets);

        List<Vet> result = vetService.findAll();

        assertEquals(2, result.size());
        verify(vetRepository, times(1)).findAll();
    }

    @Test
    void testDeleteVet() {
        doNothing().when(vetRepository).deleteById(1L);

        vetService.delete(1L);

        verify(vetRepository, times(1)).deleteById(1L);
    }
}
