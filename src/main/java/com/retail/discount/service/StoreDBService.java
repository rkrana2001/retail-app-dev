package com.retail.discount.service;


import java.util.Set;
import java.util.UUID;
import com.retail.discount.beans.Product;

public interface StoreDBService {

    Set<Product> getInventory();

    boolean isTransactionAllowed(UUID pid, int quantity);

    void updateInventory(Set<Product> cartProducts);
}
