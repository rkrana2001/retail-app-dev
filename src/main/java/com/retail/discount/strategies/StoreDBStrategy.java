package com.retail.discount.strategies;

import com.retail.discount.beans.Products;

public interface StoreDBStrategy {

    ThreadLocal<Products> getProductInventory();
}
