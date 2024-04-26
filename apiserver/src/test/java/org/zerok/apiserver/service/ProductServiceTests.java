package org.zerok.apiserver.service;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerok.apiserver.dto.PageRequestDTO;
import org.zerok.apiserver.dto.PageResponseDTO;
import org.zerok.apiserver.dto.ProductDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductServiceTests {

	@Autowired
	private ProductService productService;

	@Test
	public void testList() {

		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

		PageResponseDTO<ProductDTO> ResponseDTO = productService.getList(pageRequestDTO);

		log.info(ResponseDTO.getDtoList());

	}

	@Test
	public void testRegister() {

		ProductDTO productDTO = ProductDTO.builder()
			.pname("새로운 상품")
			.pdesc("신규 추가 상품입니다.")
			.price(1000)
			.build();

		productDTO.setUploadFileNames(
			java.util.List.of(
				UUID.randomUUID() + "_" + "Test1.jpg",
				UUID.randomUUID() + "_" + "Test2.jpg"));

		productService.register(productDTO);
	}
}