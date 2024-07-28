package com.retail.discount.strategies;

import java.util.Set;
import com.retail.discount.beans.Product;
import com.retail.discount.exceptions.InventoryShortageException;

public interface CartLoadingStrategy {

    Set<Product> loadNEachFromInventory(Set<Product> inventory, int loadQuantity) throws InventoryShortageException;
}
