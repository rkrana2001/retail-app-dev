package com.retail.discount.strategies;

import com.retail.discount.beans.Shopper;

public interface InvoicingStrategy {

    void generate(Shopper shopper);
}
