package com.retail.discount.strategies.impl;

import com.retail.discount.beans.Product;
import com.retail.discount.exceptions.InventoryShortageException;
import com.retail.discount.helper.Utility;
import com.retail.discount.strategies.CartLoadingStrategy;

import java.util.LinkedHashSet;
import java.util.Set;

public class CartLoadingStrategyImpl implements CartLoadingStrategy {

    @Override
    public Set<Product> loadNEachFromInventory(Set<Product> inventory, int loadQuantity)
            throws InventoryShortageException {
        Set<Product> cartProducts = new LinkedHashSet<>();
        for (Product p : inventory) {
            Product clone = null;
            try {
                clone = (Product) p.clone();
            } catch (CloneNotSupportedException e) {
                Utility.println(e.getMessage());
                e.printStackTrace();
            }
            if(loadQuantity <= p.getQuantity()){
                clone.setQuantity(loadQuantity);
            }
            else {
                throw new InventoryShortageException();
            }
            cartProducts.add(clone);
        }
        return cartProducts;
    }
}
