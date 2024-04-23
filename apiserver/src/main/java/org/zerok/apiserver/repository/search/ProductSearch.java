package org.zerok.apiserver.repository.search;

import org.zerok.apiserver.dto.PageRequestDTO;
import org.zerok.apiserver.dto.PageResponseDTO;
import org.zerok.apiserver.dto.ProductDTO;

public interface ProductSearch {

    PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO);

}
