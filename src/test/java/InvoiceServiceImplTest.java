import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.InvoiceDetail;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)

class InvoiceServiceImplTest {
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Set<InvoiceDetail> invoiceDetailSet = new HashSet<>() {
        {
            add(new InvoiceDetail(1L, 15, 16F, null, null));
            add(new InvoiceDetail(2L, 16, 17F, null, null));
            add(new InvoiceDetail(3L, 17, 18F, null, null));
        }
    };


    @Test
    void testRetrieveAllInvoices() throws ParseException {
        List<Invoice> invoiceList = new ArrayList<>() {
            {
                add(new Invoice(2L, 12F, 15F, dateFormat.parse("2024-10-19"), dateFormat.parse("2024-10-19"), true,invoiceDetailSet,null));
                add(new Invoice(3L, 13F, 16F, dateFormat.parse("2024-10-19"), dateFormat.parse("2024-10-19"), true,invoiceDetailSet,null));
                add(new Invoice(4L, 14F, 17F, dateFormat.parse("2024-10-19"), dateFormat.parse("2024-10-19"), true,invoiceDetailSet,null));
            }
        };

        when(invoiceService.retrieveAllInvoices()).thenReturn(invoiceList);
        List<Invoice> invoiceListTest = invoiceService.retrieveAllInvoices();
        assertEquals(3, invoiceListTest.size());
        System.out.println("Retrieve all invoices is working correctly...!!");
    }


    @Test
    void testCancelInvoice() throws ParseException {
        Invoice invoice1=new Invoice(2L, 12F, 15F, dateFormat.parse("2024-10-19"), dateFormat.parse("2024-10-19"), true,invoiceDetailSet,null);
        when(invoiceRepository.findById(invoice1.getIdInvoice())).thenReturn(Optional.of(invoice1));
        invoiceService.cancelInvoice(invoice1.getIdInvoice());
        assertTrue(invoice1.getArchived());
        verify(invoiceRepository, times(1)).findById(invoice1.getIdInvoice());
        verify(invoiceRepository, times(1)).save(invoice1);

    }


    @Test
    void testRetrieveInvoice() throws ParseException {
        Invoice invoice2 = new Invoice(6L, 12F, 15F, dateFormat.parse("2024-10-19"), dateFormat.parse("2024-10-19"), true,invoiceDetailSet,null);
        when(invoiceRepository.findById(invoice2.getIdInvoice())).thenReturn(Optional.of(invoice2));
        invoiceService.retrieveInvoice(invoice2.getIdInvoice());
        assertNotNull(invoice2);
        verify(invoiceRepository, times(1)).findById(invoice2.getIdInvoice());


    }

    @Test
    void testGetInvoicesBySupplier() throws ParseException {
        Long idSupplier = 1L;
        Supplier supplier = new Supplier(1L,"code","label",null,null); // Create a sample supplier
        Invoice invoice3 = new Invoice(7L, 12F, 15F, dateFormat.parse("2024-10-19"), dateFormat.parse("2024-10-19"), true, invoiceDetailSet, supplier);
        Set<Invoice> invoiceSet=new HashSet<>();
        invoiceSet.add(invoice3);
        supplier.setInvoices(invoiceSet);

        when(supplierRepository.findById(idSupplier)).thenReturn(Optional.of(supplier));

        List<Invoice> retrievedInvoices = invoiceService.getInvoicesBySupplier(idSupplier);

        assertNotNull(retrievedInvoices);
        assertEquals(1, retrievedInvoices.size());
        verify(supplierRepository, times(1)).findById(idSupplier);
    }
    @Test
    void testAssignOperatorToInvoice()  throws ParseException {
        Long idOperator = 1L;

        Invoice invoice4=new Invoice(8L,12F,15F,dateFormat.parse("2024-10-19"),dateFormat.parse("2024-10-19"),true,invoiceDetailSet,null);
        Operator operator=new Operator(1L,"istabrak","zouabi","password",null);
        when(invoiceRepository.findById(invoice4.getIdInvoice())).thenReturn(Optional.of(invoice4));
        when(operatorRepository.findById(idOperator)).thenReturn(Optional.of(operator));
        Set<Invoice> invoiceSet=new HashSet<>();
        invoiceSet.add(invoice4);
        operator.setInvoices(invoiceSet);
        when(operatorRepository.save(operator)).thenReturn(operator);
        invoiceService.assignOperatorToInvoice(operator.getIdOperateur(),invoice4.getIdInvoice() );
        assertTrue(operator.getInvoices().contains(invoice4));
        verify(invoiceRepository, times(1)).findById(invoice4.getIdInvoice());
    }



    @Test
    void testGetTotalAmountInvoiceBetweenDates() throws ParseException{

        Date startDate = dateFormat.parse("2024-10-19");
        Date endDate = dateFormat.parse("2024-10-31");
        float expectedTotalAmount = 100.0F; // Set your expected total amount
        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate)).thenReturn(expectedTotalAmount);
        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);
        assertEquals(expectedTotalAmount, totalAmount, 0.01); // Use a delta for float comparison
        verify(invoiceRepository, times(1)).getTotalAmountInvoiceBetweenDates(startDate, endDate);
    }

}





