import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@Slf4j
class StockServiceImplTest {
    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    void testRetrieveStock() {
        Stock stock2 = new Stock(3L, "Machines à laver", null);

        Mockito.lenient().when(stockRepository.findById(3L)).thenReturn(Optional.of(stock2));
        stockService.retrieveStock(3L);
        assertNotNull(stock2);

        System.out.println("get ==> " + "id Stock: " +stock2.getIdStock()+ " & title: "+stock2.getTitle());
        System.out.println(" Retrieve is working correctly...!!");
    }


    @Test
    void testAddStock1() {
        Stock stock1 = new Stock(2L, "TV", null);
        stockService.addStock(stock1);
        verify(stockRepository, times(1)).save(stock1);
        System.out.println(" Add is working correctly...!!");
    }


    @Test
    void testRetrieveAllStock1() {
        List<Stock> StockList = new ArrayList<>() {
            {
                add(new Stock(1L,"mmm",null));
                add(new Stock(2L,"sss",null));
                add(new Stock(3L,"www",null));
            }};

        when(stockService.retrieveAllStock()).thenReturn(StockList);
        //test
        List<Stock> sList = stockService.retrieveAllStock();
        assertEquals(3, sList.size());
        System.out.println(" Retrieve All is working correctly...!!");

    }











}