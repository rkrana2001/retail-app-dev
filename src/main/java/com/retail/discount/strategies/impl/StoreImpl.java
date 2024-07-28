package com.retail.discount.strategies.impl;

import com.retail.discount.strategies.StoreDBStrategy;
import com.retail.discount.beans.Products;
import com.retail.discount.beans.Product;
import com.retail.discount.constants.ProductTypes;

import java.util.UUID;

public class StoreImpl implements StoreDBStrategy {

    private final ThreadLocal<Products> productInventory = new ThreadLocal<>();

    private StoreImpl() {
        productInventory.set(initialize());
    }

    private static final class StoreLoader {
        private static final StoreImpl singleton = new StoreImpl();
    }

    public static final StoreImpl getStore() {
        return StoreLoader.singleton;
    }

    @Override
    public final ThreadLocal<Products> getProductInventory() {
        return productInventory;
    }

    private final Products initialize() {
        Products inventory = new Products();

        //Add Product 1
        UUID pid = UUID.randomUUID();
        Product p = new Product(pid, "Vanheusen LM31 M", 20, ProductTypes.CLOTHING, 19.99d);
        inventory.getProducts().put(pid, p);

        //Add Product 2
        pid = UUID.randomUUID();
        p = new Product(pid, "Gillete Vector Blue", 62, ProductTypes.COSMETICS, 4.99d);
        inventory.getProducts().put(pid, p);

        //Add Product 3
        pid = UUID.randomUUID();
        p = new Product(pid, "Transcend 64GB P320", 120, ProductTypes.ELECTRONICS, 14.99d);
        inventory.getProducts().put(pid, p);

        //Add Product 4
        pid = UUID.randomUUID();
        p = new Product(pid, "Tomato", 20, ProductTypes.GROCERY, 0.99d);
        inventory.getProducts().put(pid, p);

        //Add Product 5
        pid = UUID.randomUUID();
        p = new Product(pid, "Luxor Black Royal Smooth", 45, ProductTypes.STATIONERY, 1.99d);
        inventory.getProducts().put(pid, p);

        return inventory;
    }
}
