import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import tn.esprit.devops_project.services.OperatorServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)


public class OperatorServiceImplTest {


    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorServiceImpl operatorServiceImpl;


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

}
