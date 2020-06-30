package com.cafe24.memory.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cafe24.memory.domain.SearchReportAnimal;

@Mapper
public interface AnimalReportMapper {
	/**
	 *유기동물 신고 INSERT
	 */ 
	public int insertAnimalReport(SearchReportAnimal searchReportAnimal);
	/**
	 *유기동물 신고리스트를위한  SELECT
	 */ 
	
}
