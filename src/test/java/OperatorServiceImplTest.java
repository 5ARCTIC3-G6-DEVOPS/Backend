import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import tn.esprit.devops_project.services.OperatorServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)


public class OperatorServiceImplTest {


    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorServiceImpl operatorServiceImpl;

/////////////////addOperatortest////////////

    @Test
    public void testAddOperator() {
        // Arrange : création d'un opérateur fictif
        Operator operator = new Operator();
        operator.setLname("Operator1");

        // Configure le mock pour qu'il retourne l'opérateur quand save() est appelé
        when(operatorRepository.save(operator)).thenReturn(operator);

        // Act : appelle la méthode à tester
        Operator result = operatorServiceImpl.addOperator(operator);

        // Assert : vérifie le résultat
        assertEquals("Operator1", result.getLname(), "Le nom de l'opérateur doit être 'Operator1'");
        verify(operatorRepository, times(1)).save(operator);  // Vérifie que save() a été appelé une fois
    }


    //////////RetrieveAll////////////////


    @Test
    public void testRetrieveAll() {
        // Arrange
        Operator operator1 = new Operator();
        Operator operator2 = new Operator();

        List<Operator> operators = Arrays.asList(operator1 , operator2);
        when(operatorRepository.findAll()).thenReturn(operators);

        // Act
        List<Operator> result = operatorServiceImpl.retrieveAllOperators();

        // Assert
        assertEquals(2, result.size());
        verify(operatorRepository, times(1)).findAll();
    }

    /////////////////Delete////////

    @Test
    public void testDeleteOperator() {
        // Arrange : l'identifiant de l'opérateur à supprimer
        Long idOperateur = 1L;

        // Act : appelle la méthode à tester
        operatorServiceImpl.deleteOperator(idOperateur);

        // Assert : vérifie que la méthode deleteById a été appelée avec le bon identifiant
        verify(operatorRepository, times(1)).deleteById(idOperateur);
    }


    //////////////////////RetrieveOperator////////


    @Test
    public void testRetrieveOperator_Success() {
        // Arrange
        Long IdOperateur = 1L;
        Operator operator = new Operator();
        operator.setIdOperateur(IdOperateur);

        // Simule un retour d'un Operator pour l'identifiant donné
        when(operatorRepository.findById(IdOperateur)).thenReturn(Optional.of(operator));

        // Act
        Operator result = operatorServiceImpl.retrieveOperator(IdOperateur);

        // Assert
        assertNotNull(result);
        assertEquals(IdOperateur, result.getIdOperateur());
        verify(operatorRepository, times(1)).findById(IdOperateur);
    }

    @Test
    public void testRetrieveOperator_NotFound() {
        // Arrange
        Long IdOperateur = 2L;

        // Simule un retour vide pour l'identifiant donné
        when(operatorRepository.findById(IdOperateur)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            operatorServiceImpl.retrieveOperator(IdOperateur);
        });

        assertEquals("Operator not found", exception.getMessage());
        verify(operatorRepository, times(1)).findById(IdOperateur);
    }


    ///////////////Update///////////////////////

    @Test
    public void testUpdateOperator() {
        // Arrange
        Operator operator = new Operator();
        operator.setIdOperateur(1L); // Assurez-vous que l'ID est défini pour simuler une mise à jour
        operator.setLname("Updated Name");

        when(operatorRepository.save(operator)).thenReturn(operator);

        // Act
        Operator updatedOperator = operatorServiceImpl.updateOperator(operator);

        // Assert
        assertNotNull(updatedOperator);
        assertEquals(operator.getLname(), updatedOperator.getLname());
        verify(operatorRepository, times(1)).save(operator);
    }



}


