package com.retail.discount.service.impl;

import com.retail.discount.beans.Product;
import com.retail.discount.beans.ShoppingCart;
import com.retail.discount.exceptions.InventoryShortageException;
import com.retail.discount.service.ShoppingCartService;
import com.retail.discount.service.StoreDBService;
import com.retail.discount.strategies.CartLoadingStrategy;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class CartServiceImpl implements ShoppingCartService {

    private final StoreDBService myStoreDBService;
    private final CartLoadingStrategy myCartLoadingStrategy;

    public CartServiceImpl(StoreDBService myStoreDBService,
                           CartLoadingStrategy myCartLoadingStrategy) {
        this.myStoreDBService = myStoreDBService;
        this.myCartLoadingStrategy = myCartLoadingStrategy;
    }

    @Override
    public boolean addProduct(Product product, ShoppingCart shoppingCart) {
        boolean response = false;
        LinkedHashMap<UUID, Product> productsInCart = shoppingCart.getProductsInCart().getProducts();
        if (!productsInCart.containsValue(product)) {
            productsInCart.put(product.getId(), product);
            response = true;
        }
        return response;
    }

    @Override
    public boolean addProductBatch(Set<Product> products, ShoppingCart shoppingCart) {
        boolean response = false;
        for(Product p : products){
            response = addProduct(p, shoppingCart);
            if(!response){
                break;
            }
        }
        return response;
    }

    @Override
    public boolean removeProduct(UUID pid, ShoppingCart shoppingCart) {
        boolean response = false;
        LinkedHashMap<UUID, Product> productsInCart = shoppingCart.getProductsInCart().getProducts();
        if (productsInCart.containsKey(pid)) {
            productsInCart.remove(pid);
            response = true;
        }
        return response;
    }

    @Override
    public boolean updateProduct(Product product, ShoppingCart shoppingCart) {
        boolean response = false;
        LinkedHashMap<UUID, Product> productsInCart = shoppingCart.getProductsInCart().getProducts();
        if (productsInCart.containsKey(product.getId())) {
            productsInCart.put(product.getId(), product);
            response = true;
        }
        return response;
    }

    @Override
    public Product getProduct(UUID pid, ShoppingCart shoppingCart) {
        return shoppingCart.getProductsInCart().getProducts().get(pid);
    }

    @Override
    public Set<Product> getAllProducts(ShoppingCart shoppingCart) {
        return new LinkedHashSet<>(shoppingCart.getProductsInCart().getProducts().values());
    }

    @Override
    public boolean loadNEachFromInventory(int loadQuantity, ShoppingCart shoppingCart) throws InventoryShortageException {
        return addProductBatch(myCartLoadingStrategy
                .loadNEachFromInventory(myStoreDBService.getInventory(),loadQuantity), shoppingCart);
    }

    public ShoppingCart getNewShoppingCart() {
        return new ShoppingCart();
    }
}
