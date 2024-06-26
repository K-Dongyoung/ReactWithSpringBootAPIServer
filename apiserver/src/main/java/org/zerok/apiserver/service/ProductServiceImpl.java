package org.zerok.apiserver.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerok.apiserver.domain.Product;
import org.zerok.apiserver.domain.ProductImage;
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

		// 프로젝션으로 하는 방법도 있다고 합니다
		List<ProductDTO> dtoList = result.get().map(arr -> {
			Product product = (Product) arr[0];
			ProductImage productImage = (ProductImage)arr[1];
			ProductDTO productDTO = ProductDTO.builder()
				.pno(product.getPno())
				.pname(product.getPname())
				.price(product.getPrice())
				.pdesc(product.getPdesc())
				.build();

			String imageStr = productImage.getFileName();
			productDTO.setUploadFileNames(List.of(imageStr));

			return productDTO;
		}).collect(Collectors.toList());

		long totalCount = result.getTotalElements();

		return PageResponseDTO.<ProductDTO>withAll()
			.dtoList(dtoList)
			.totalCount(totalCount)
			.pageRequestDTO(pageRequestDTO)
			.build();
	}

	@Override
	public Long register(ProductDTO productDTO) {

		Product product = dtoToEntity(productDTO);

		log.info("==================================================");
		log.info(product);
		log.info(product.getImageList());

        return productRepository.save(product).getPno();
	}

	@Override
	public ProductDTO get(Long pno) {

		Optional<Product> result = productRepository.findById(pno);

		Product product = result.orElseThrow();

		return EntityToDTO(product);
	}

	@Override
	public void modify(ProductDTO productDTO) {

		Product product = productRepository.findById(productDTO.getPno()).orElseThrow();

		product.changeName(productDTO.getPname());
		product.changeDesc(productDTO.getPdesc());
		product.changePrice(productDTO.getPrice());
		product.changeDelFlag(productDTO.isDelFlag());

		List<String> uploadFileNames = productDTO.getUploadFileNames();

		product.clearList();

		if (uploadFileNames != null && !uploadFileNames.isEmpty()) {

			uploadFileNames.forEach(product::addImageString);

		}

		productRepository.save(product);

	}

	@Override
	public void remove(Long pno) {

		productRepository.deleteById(pno);

	}

	private ProductDTO EntityToDTO(Product product) {

		ProductDTO productDTO = ProductDTO.builder()
			.pno(product.getPno())
			.pname(product.getPname())
			.pdesc(product.getPdesc())
			.price(product.getPrice())
			.delFlag(product.isDelFlag())
			.build();

		List<ProductImage> imageList = product.getImageList();

		if (imageList == null || imageList.isEmpty()) {
			return productDTO;
		}

		List<String> fileNameList = imageList.stream().map(ProductImage::getFileName).toList();

		productDTO.setUploadFileNames(fileNameList);

		return productDTO;
	}

	private Product dtoToEntity(ProductDTO productDTO) {

		Product product = Product.builder()
			.pno(productDTO.getPno())
			.pname(productDTO.getPname())
			.pdesc(productDTO.getPdesc())
			.price(productDTO.getPrice())
			.build();

		List<String> uploadFileNames = productDTO.getUploadFileNames();

		if (uploadFileNames == null || uploadFileNames.isEmpty()) {
			return product;
		}

		uploadFileNames.forEach(product::addImageString);

		return product;

	}

}
