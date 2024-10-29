import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Operator;

import tn.esprit.devops_project.exceptions.OperatorNotFoundException; // Assurez-vous d'avoir cette classe
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.services.OperatorServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperatorServiceImplTest {

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorServiceImpl operatorServiceImpl;

    // Test pour l'ajout d'un opérateur
    @Test
    public void testAddOperator() {
        Operator operator = new Operator();
        operator.setLname("Operator1");

        when(operatorRepository.save(operator)).thenReturn(operator);

        Operator result = operatorServiceImpl.addOperator(operator);

        assertEquals("Operator1", result.getLname(), "Le nom de l'opérateur doit être 'Operator1'");
        verify(operatorRepository, times(1)).save(operator);
    }

    // Test pour récupérer tous les opérateurs
    @Test
    public void testRetrieveAll() {
        Operator operator1 = new Operator();
        Operator operator2 = new Operator();
        List<Operator> operators = Arrays.asList(operator1, operator2);

        when(operatorRepository.findAll()).thenReturn(operators);

        List<Operator> result = operatorServiceImpl.retrieveAllOperators();

        assertEquals(2, result.size());
        verify(operatorRepository, times(1)).findAll();
    }

    // Test pour la suppression d'un opérateur
    @Test
    public void testDeleteOperator() {
        Long idOperateur = 1L;

        operatorServiceImpl.deleteOperator(idOperateur);

        verify(operatorRepository, times(1)).deleteById(idOperateur);
    }

    // Test pour récupérer un opérateur par son ID (succès)
    @Test
    public void testRetrieveOperator_Success() {
        Long idOperateur = 1L;
        Operator operator = new Operator();
        operator.setIdOperateur(idOperateur);

        when(operatorRepository.findById(idOperateur)).thenReturn(Optional.of(operator));

        Operator result = operatorServiceImpl.retrieveOperator(idOperateur);

        assertNotNull(result);
        assertEquals(idOperateur, result.getIdOperateur());
        verify(operatorRepository, times(1)).findById(idOperateur);
    }

    public class OperatorNotFoundException extends RuntimeException {
        public OperatorNotFoundException(String message) {
            super(message);
        }
    }

    // Test pour récupérer un opérateur par son ID (non trouvé)
    @Test
    public void testRetrieveOperator_NotFound() {
        // Arrange
        Long IdOperateur = 2L;

        // Simule un retour vide pour l'identifiant donné
        when(operatorRepository.findById(IdOperateur)).thenReturn(Optional.empty());

        // Act & Assert
        OperatorNotFoundException exception = assertThrows(OperatorNotFoundException.class, () -> {
            operatorServiceImpl.retrieveOperator(IdOperateur);
        });

        assertEquals("Operator not found", exception.getMessage());
        verify(operatorRepository, times(1)).findById(IdOperateur);
    }

    // Test pour la mise à jour d'un opérateur
    @Test
    public void testUpdateOperator() {
        Operator operator = new Operator();
        operator.setIdOperateur(1L);
        operator.setLname("Updated Name");

        when(operatorRepository.save(operator)).thenReturn(operator);

        Operator updatedOperator = operatorServiceImpl.updateOperator(operator);

        assertNotNull(updatedOperator);
        assertEquals(operator.getLname(), updatedOperator.getLname());
        verify(operatorRepository, times(1)).save(operator);
    }
}
