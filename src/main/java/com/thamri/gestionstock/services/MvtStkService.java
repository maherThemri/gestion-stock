package com.thamri.gestionstock.services;

import java.math.BigDecimal;
import java.util.List;

import com.thamri.gestionstock.dto.MvtStkDto;

public interface MvtStkService {
	
	BigDecimal stockReelArticle(Integer idArticle);

	List<MvtStkDto> mvtStkArticle(Integer idArticle);

	MvtStkDto entreeStock(MvtStkDto dto);

	MvtStkDto sortieStock(MvtStkDto dto);

	MvtStkDto correctionStockPos(MvtStkDto dto);

	MvtStkDto correctionStockNeg(MvtStkDto dto);
}
