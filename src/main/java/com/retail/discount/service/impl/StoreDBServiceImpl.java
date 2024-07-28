package com.retail.discount.service.impl;

import com.retail.discount.beans.Product;
import com.retail.discount.dao.StoreDao;
import com.retail.discount.service.StoreDBService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class StoreDBServiceImpl implements StoreDBService {

    StoreDao storeDao;

    public StoreDBServiceImpl(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    @Override
    public Set<Product> getInventory() {
        return storeDao.getAllProducts();
    }

    @Override
    public boolean isTransactionAllowed(UUID pid, int quantity) {
        boolean response = false;
        Product product = storeDao.getProduct(pid);
        if (product != null && product.getQuantity() >= quantity) {
            response = true;
        }
        return response;
    }

    @Override
    public void updateInventory(Set<Product> cartProducts) {
        Set<Product> inventoryToUpdate = new LinkedHashSet<>(cartProducts.size());
        for (Product p : cartProducts) {
            Product storeProduct = storeDao.getProduct(p.getId());
            if (isTransactionAllowed(p.getId(), p.getQuantity())) {
                storeProduct.setQuantity(storeProduct.getQuantity() - p.getQuantity());
                inventoryToUpdate.add(storeProduct);
            }
        }
        if (!inventoryToUpdate.isEmpty()) {
            storeDao.updateInventoryBatch(inventoryToUpdate);
        }
    }

}
