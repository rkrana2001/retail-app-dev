package com.retail.discount.service;

import com.retail.discount.beans.Shopper;

public interface InvoiceService {
    void generate(Shopper shopper);
    void print(Shopper shopper);
}
