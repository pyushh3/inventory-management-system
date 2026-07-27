package com.clothing.inventory.dashboard.dto;

import java.util.List;

public class DashboardResponseDto {

    private long totalProducts;
    private long totalStock;
    private long totalCategories;
    private long totalSuppliers;

    private List<LowStockDto> lowStockProducts;

    private List<RecentPurchaseDto> recentPurchases;

    public List<RecentPurchaseDto> getRecentPurchases() {
        return recentPurchases;
    }

    public void setRecentPurchases(List<RecentPurchaseDto> recentPurchases) {
        this.recentPurchases = recentPurchases;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(long totalStock) {
        this.totalStock = totalStock;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public long getTotalSuppliers() {
        return totalSuppliers;
    }

    public void setTotalSuppliers(long totalSuppliers) {
        this.totalSuppliers = totalSuppliers;
    }

    public List<LowStockDto> getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(List<LowStockDto> lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }
}
