package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.TotalSalesSellerSummaryProjection;

public class TotalSalesSellerSummaryDTO {

    private String sellerName;
    private Double total;

    public TotalSalesSellerSummaryDTO() {
    }

    public TotalSalesSellerSummaryDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public TotalSalesSellerSummaryDTO(TotalSalesSellerSummaryProjection projection) {
        sellerName = projection.getSellerName();
        this.total = projection.getTotal();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
