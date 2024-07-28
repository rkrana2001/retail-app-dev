package com.retail.discount.dao;

import com.retail.discount.beans.Product;

import java.util.Set;
import java.util.UUID;

public interface StoreDao {

    boolean updateInventoryBatch(Set<Product> products);

    boolean updateInventory(Product product);

    Product getProduct(UUID pid);

    Set<Product> getAllProducts();
}
