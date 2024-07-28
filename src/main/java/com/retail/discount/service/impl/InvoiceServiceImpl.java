package com.retail.discount.service.impl;

import com.retail.discount.beans.Shopper;
import com.retail.discount.constants.ApplicationConstants;
import com.retail.discount.helper.Utility;
import com.retail.discount.service.InvoiceService;
import com.retail.discount.strategies.InvoicingStrategy;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoicingStrategy invoicingStrategy;

    public InvoiceServiceImpl(InvoicingStrategy invoicingStrategy) {
        this.invoicingStrategy = invoicingStrategy;
    }

    @Override
    public void generate(Shopper shopper) {
        invoicingStrategy.generate(shopper);
    }

    @Override
    public void print(Shopper shopper) {
        //Bill Header
        Utility.printBuffer();
        Utility.printCenter(ApplicationConstants.BILL_HEADER.getApplicationConstant().toString(),
                ApplicationConstants.BILL_PADDING.getApplicationConstant().toString());
        Utility.printBuffer();
        Utility.println(" ");

        //Bill & User Info
        Utility.printBillMeta(shopper);
        Utility.println(" ");

        //Bill Products Info
        Utility.printBuffer();
        Utility.printCenter(ApplicationConstants.BILL_PRODUCT_HEADER.getApplicationConstant().toString(),
                ApplicationConstants.BILL_PADDING.getApplicationConstant().toString());
        Utility.printProducts(shopper.getShoppingCart().getProductsInCart().getProducts().values());
        Utility.printColumn("Total Discount = $" + ApplicationConstants.df.format(shopper.getInvoice().getDiscount()),
                "Discounted Bill = $" + ApplicationConstants.df.format(shopper.getInvoice().getAmount()),
                ApplicationConstants.BILL_SPACE.getApplicationConstant().toString());
        Utility.printBuffer();
    }



}
