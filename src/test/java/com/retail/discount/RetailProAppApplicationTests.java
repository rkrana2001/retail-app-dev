package com.retail.discount;

import com.retail.discount.beans.UserDetails;
import com.retail.discount.constants.UserTypes;
import com.retail.discount.controller.RetailProAppApplicationController;
import com.retail.discount.dao.impl.StoreDaoImpl;
import com.retail.discount.exceptions.InventoryShortageException;
import com.retail.discount.service.InvoiceService;
import com.retail.discount.service.StoreDBService;
import com.retail.discount.service.impl.CartServiceImpl;
import com.retail.discount.service.impl.InvoiceServiceImpl;
import com.retail.discount.service.impl.StoreDBServiceImpl;
import com.retail.discount.strategies.impl.CartLoadingStrategyImpl;
import com.retail.discount.strategies.impl.InvoiceGeneratorImpl;
import com.retail.discount.strategies.impl.StoreImpl;
import org.junit.Before;
import org.junit.Test;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

//@SpringBootApplication
public class RetailProAppApplicationTests {

    private StoreDBService myStoreDBService;
    private CartServiceImpl myCartService;
    private InvoiceService myInvoiceService;
    private DecimalFormat df;

    @Before
    public void setUp() throws Exception {
        //Services
        myStoreDBService = new StoreDBServiceImpl(new StoreDaoImpl(StoreImpl.getStore()));
        myCartService = new CartServiceImpl(myStoreDBService, new CartLoadingStrategyImpl());
        myInvoiceService = new InvoiceServiceImpl(new InvoiceGeneratorImpl());
        df = new DecimalFormat("#.####");
    }

    @Test
    public void UserIsAffiliate() throws InventoryShortageException {
        RetailProAppApplicationController shoppingApplication = new RetailProAppApplicationController(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Roopesh Rana", UserTypes.AFFILIATE, localDateTime, "+91-9891-000-001", "rana.roop@gmail");
        assertEquals("Bill Amount must me 73.5336 for this User",df.format(73.5336d), df.format(shoppingApplication.shop(userDetails)));
    }

    @Test
    public void UserIsEmployee() throws InventoryShortageException {
        RetailProAppApplicationController shoppingApplication = new RetailProAppApplicationController(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Roopesh Rana", UserTypes.EMPLOYEE, localDateTime, "+91-9891-000-001", "rana.roop@gmail");
        assertEquals("Bill Amount must me 57.1928 for this User",df.format(57.1928d), df.format(shoppingApplication.shop(userDetails)));
    }

    @Test
    public void UserIsRecentCustomer() throws InventoryShortageException {
        RetailProAppApplicationController shoppingApplication = new RetailProAppApplicationController(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Roopesh Rana", UserTypes.CUSTOMER, localDateTime, "+91-9891-000-001", "rana.roop@gmail.com");
        assertNotSame("Bill Amount must me 81.7040 for this User",df.format(81.7040d), df.format(shoppingApplication.shop(userDetails)));
    }

    @Test
    public void UserIsOldCustomer() throws InventoryShortageException {
        RetailProAppApplicationController shoppingApplication = new RetailProAppApplicationController(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2015, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Roopesh Rana", UserTypes.CUSTOMER, localDateTime, "+91-9891-000-001", "rana.roop@gmail");
        assertEquals("Bill Amount must me 77.6188 for this User",df.format(77.6188d), df.format(shoppingApplication.shop(userDetails)));
    }
}
