package org.zerok.apiserver.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerok.apiserver.dto.PageRequestDTO;
import org.zerok.apiserver.dto.PageResponseDTO;
import org.zerok.apiserver.dto.ProductDTO;

@Transactional
public interface ProductService {

	PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

	Long register(ProductDTO productDTO);

	ProductDTO get(Long pno);

	void modify(ProductDTO productDTO);

	void remove(Long pno);
}
