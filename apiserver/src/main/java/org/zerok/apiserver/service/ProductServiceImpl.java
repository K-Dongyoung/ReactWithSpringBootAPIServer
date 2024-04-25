package org.zerok.apiserver.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerok.apiserver.dto.PageRequestDTO;
import org.zerok.apiserver.dto.PageResponseDTO;
import org.zerok.apiserver.dto.ProductDTO;
import org.zerok.apiserver.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {

		Pageable pageable = PageRequest.of(
			pageRequestDTO.getPage() - 1,
			pageRequestDTO.getSize(),
			Sort.by("pno").descending());

		Page<Object[]> result = productRepository.selectList(pageable);

		List<ProductDTO> dtoList = result.get().map(arr -> {
			ProductDTO productDTO = null;
			return productDTO;
		}).toList();

		long totalCount = result.getTotalElements();

		return PageResponseDTO.<ProductDTO>withAll().build();
	}
}
