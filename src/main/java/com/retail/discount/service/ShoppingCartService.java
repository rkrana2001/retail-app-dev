package com.retail.discount.service;

import com.retail.discount.beans.Product;
import com.retail.discount.beans.ShoppingCart;
import com.retail.discount.exceptions.InventoryShortageException;

import java.util.Set;
import java.util.UUID;

public interface ShoppingCartService {

    boolean addProductBatch(Set<Product> products, ShoppingCart shoppingCart);

    boolean removeProduct(UUID pid, ShoppingCart shoppingCart);

    boolean updateProduct(Product product, ShoppingCart shoppingCart);

    Product getProduct(UUID pid, ShoppingCart shoppingCart);

    Set<Product> getAllProducts(ShoppingCart shoppingCart);

    boolean loadNEachFromInventory(int loadQuantity, ShoppingCart shoppingCart) throws InventoryShortageException;

    boolean addProduct(Product product, ShoppingCart shoppingCart);

    ShoppingCart getNewShoppingCart();
}
