

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.DevOps_ProjectSpringBootApplication;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest(classes = DevOps_ProjectSpringBootApplication.class)
class StockServiceImplTestJUnit {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockServiceImpl stockService;

    private Stock testStock,s;

    @BeforeEach
    public void setUp() {
        testStock = new Stock(1L, "Test Stock", null);
    }

   @AfterEach
    public void tearDown() {
        stockRepository.deleteAll();
    }

    @Test
    void testAddStock() {
        s = stockService.addStock(testStock);
        Assertions.assertNotNull(s.getTitle());
        log.info("Added Stock: {}", s.getIdStock() + " Title: " + s.getTitle());
    }

    @Test
    void testRetrieveAllStock() {
        List<Stock> stockList = new ArrayList<>() {
            {
                add(new Stock(3L, "Stock 1", null));
                add(new Stock(4L, "Stock 2", null));
                add(new Stock(5L, "Stock 3", null));
                add(new Stock(6L, "Stock 4", null));
            }
        };
        stockRepository.saveAll(stockList);
        List<Stock> retrievedList = stockService.retrieveAllStock();
        assertEquals(4, retrievedList.size());
        log.info("Retrieved Stock List: {}", retrievedList);
    }


}
