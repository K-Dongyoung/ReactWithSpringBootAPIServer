package org.zerok.apiserver.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerok.apiserver.dto.PageRequestDTO;
import org.zerok.apiserver.dto.PageResponseDTO;
import org.zerok.apiserver.dto.ProductDTO;
import org.zerok.apiserver.service.ProductService;
import org.zerok.apiserver.util.CustomFileUtil;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;

    private final ProductService productService;

    // @PostMapping("/")
    // public Map<String, String> register(ProductDTO productDTO) {
    //
    //     log.info("register: " + productDTO);
    //
    //     List<MultipartFile> files = productDTO.getFiles();
    //
    //     List<String> uploadedFileNames = fileUtil.saveFiles(files);
    //
    //     productDTO.setUploadFileNames(uploadedFileNames);
    //
    //     log.info(uploadedFileNames);
    //
    //     return Map.of("RESULT", "SUCCESS");
    // }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName) {

        return fileUtil.getFile(fileName);

    }

    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {

        return productService.getList(pageRequestDTO);

    }

    @PostMapping("/")
    public Map<String, Long> register(ProductDTO productDTO) {

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = fileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames);

        Long pno = productService.register(productDTO);

        return Map.of("result", pno);

    }

    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable("pno") Long pno) {
        return productService.get(pno);
    }

    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable("pno") Long pno, ProductDTO productDTO) {

        return
    }
}
