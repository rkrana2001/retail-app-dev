package com.retail.discount.controller;

import com.retail.discount.beans.Shopper;
import com.retail.discount.beans.ShoppingCart;
import com.retail.discount.beans.UserDetails;
import com.retail.discount.constants.ApplicationConstants;
import com.retail.discount.exceptions.InventoryShortageException;
import com.retail.discount.service.InvoiceService;
import com.retail.discount.service.StoreDBService;
import com.retail.discount.service.impl.CartServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@Controller
public class RetailProAppApplicationController {

    private final StoreDBService myStoreDBService;
    private final CartServiceImpl myCartService;
    private final InvoiceService myInvoiceService;

    public RetailProAppApplicationController(StoreDBService myStoreDBService,
                                             CartServiceImpl myCartService,
                                             InvoiceService myInvoiceService) {
        this.myStoreDBService = myStoreDBService;
        this.myCartService = myCartService;
        this.myInvoiceService = myInvoiceService;
    }

    public double shop(UserDetails userDetails) throws InventoryShortageException {

        ShoppingCart shoppingCart = myCartService.getNewShoppingCart();
        Shopper shopper = new Shopper(userDetails, shoppingCart);

        //Shopping Business Logic
        myCartService.loadNEachFromInventory(
                (int) ApplicationConstants.CART_QUANTITY.getApplicationConstant(),
                shoppingCart);

        myStoreDBService.updateInventory(myCartService.getAllProducts(shoppingCart));
        myInvoiceService.generate(shopper);
        myInvoiceService.print(shopper);
        return shopper.getInvoice().getAmount();
    }


}
