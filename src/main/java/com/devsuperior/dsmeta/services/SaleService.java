package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleSellerDTO;
import com.devsuperior.dsmeta.dto.TotalSalesSellerSummaryDTO;
import com.devsuperior.dsmeta.projections.TotalSalesSellerSummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

    public Page<SaleSellerDTO> searchReport(String minDate, String maxDate, String partialName, Pageable pageable) {

        LocalDate minDateObj;
        LocalDate maxDateObj;

        if(maxDate.isEmpty()) {
            maxDateObj = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        } else {
            maxDateObj = LocalDate.parse(maxDate);
        }

        if(minDate.isEmpty()) {
            minDateObj = maxDateObj.minusYears(1L);
        } else {
            minDateObj = LocalDate.parse(minDate);
        }

        Page<Sale> sales = repository.searchReportByDateName(minDateObj, maxDateObj, partialName, pageable);
        Page<SaleSellerDTO> pageDto = sales.map(x -> new SaleSellerDTO(x));
        return pageDto;
    }

    public List<TotalSalesSellerSummaryDTO> searchSummary(String minDate, String maxDate) {

        LocalDate minDateObj;
        LocalDate maxDateObj;

        if(maxDate.isEmpty()) {
            maxDateObj = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        } else {
            maxDateObj = LocalDate.parse(maxDate);
        }

        if(minDate.isEmpty()) {
            minDateObj = maxDateObj.minusYears(1L);
        } else {
            minDateObj = LocalDate.parse(minDate);
        }

        List<TotalSalesSellerSummaryProjection> list = repository.searchSalesSummaryByDate(minDateObj, maxDateObj);
        List<TotalSalesSellerSummaryDTO> listDto = list.stream().map(x -> new TotalSalesSellerSummaryDTO(x)).collect(Collectors.toList());

        return listDto;
    }
}
