package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projections.TotalSalesSellerSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(
            value = "SELECT obj " +
                    "FROM Sale obj " +
                    "JOIN FETCH obj.seller " +
                    "WHERE obj.date BETWEEN :minDate AND :maxDate " +
                    "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :partialName, '%'))",
            countQuery = "SELECT COUNT(obj) FROM Sale obj " +
                    "JOIN obj.seller " +
                    "WHERE obj.date BETWEEN :minDate AND :maxDate " +
                    "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :partialName, '%'))")
    Page<Sale> searchReportByDateName(LocalDate minDate, LocalDate maxDate, String partialName, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT tb_seller.name AS sellerName, SUM(tb_sales.amount) AS total " +
            "FROM tb_sales INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY tb_seller.name")
    List<TotalSalesSellerSummaryProjection> searchSalesSummaryByDate(LocalDate minDate, LocalDate maxDate);

}
