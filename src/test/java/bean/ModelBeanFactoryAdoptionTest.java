package bean;

import entity.Adoption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import view.RequestAdoption;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test sviluppatato da Sara Giancarli
 * Test di integrazione su { ModelBeanFactory}.
 */
@ExtendWith(MockitoExtension.class)
class ModelBeanFactoryAdoptionTest {

    @Mock
    private RequestAdoption view;   // view mockata

    private Adoption adoptionBean;

    @BeforeEach
    void setUp() {

        /* -------- stubbing della view -------- */
        when(view.getName())           .thenReturn("Alice");
        when(view.getSurname())        .thenReturn("Wonder");
        when(view.getPhoneNumber())    .thenReturn("3331234567");
        when(view.getEmail())          .thenReturn("alice@example.com");
        when(view.getAddress())        .thenReturn("Via dei Gatti 1");
        when(view.getSelectedCatName()).thenReturn("Birba");

        /* -------- creazione bean -------- */
        adoptionBean = ModelBeanFactory.getRequestAdoptionBean(view);
    }

    /* ---------------------------------------------------------------------- */

    @Test
    void testMappingCampi() {
        assertAll(
            () -> assertEquals("Alice",  adoptionBean.getName()),
            () -> assertEquals("Wonder", adoptionBean.getSurname()),
            () -> assertEquals("3331234567", adoptionBean.getPhoneNumber()),
            () -> assertEquals("alice@example.com", adoptionBean.getEmail()),
            () -> assertEquals("Via dei Gatti 1",  adoptionBean.getAddress()),
            () -> assertEquals("Birba",  adoptionBean.getNameCat())
        );
    }

    @Test
    void testStatoIniziale() {
        assertFalse(adoptionBean.getStateAdoption(), "Lo stato iniziale deve essere false");
    }
}