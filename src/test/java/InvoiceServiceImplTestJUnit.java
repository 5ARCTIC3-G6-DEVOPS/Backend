
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.DevOps_ProjectSpringBootApplication;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest(classes = DevOps_ProjectSpringBootApplication.class)
@Slf4j
 class InvoiceServiceImplTestJUnit {
    @Autowired
    InvoiceServiceImpl invoiceService;
    @Autowired
    InvoiceRepository invoiceRepository;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Invoice invoice;
    @BeforeEach
    void setUp() throws ParseException {
         invoice = new Invoice(6L, 26F, 333F, dateFormat.parse("2024-10-27"), dateFormat.parse("2024-10-27"), false, null, null);

    }
    @Test
    void testCancelInvoice() throws ParseException {
        invoiceRepository.findById(6L);
        invoiceService.cancelInvoice(6L);
        assertFalse(invoice.getArchived());
        log.info(invoice.getArchived().toString());

    }
    @AfterEach
    void tearDown(){
        invoiceRepository.deleteAll();
    }

}
