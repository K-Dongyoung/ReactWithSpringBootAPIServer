package org.zerok.apiserver.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerok.apiserver.domain.Product;
import org.zerok.apiserver.dto.PageRequestDTO;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {

        for (int i = 0; i < 10; i++) {

            Product product = Product.builder().pname("Test" + i).pdesc("Test Desc").price(1000).build();

            product.addImageString(UUID.randomUUID() + "_" + "Image1.jpg");

            product.addImageString(UUID.randomUUID() + "_" + "Image2.jpg");

            productRepository.save(product);

        }

    }

    @Transactional // 없으면 에러남
    @Test
    public void testRead() {
        Long pno = 1L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());
    }

    @Test
    public void testRead2() {
        Long pno = 1L;

        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());
    }

    // DB 호출 한번에 할 수 있음.
    @Commit
    @Transactional
    @Test
    public void testDelete() {

        Long pno = 1L;

        productRepository.updateToDelete(pno, false);
    }

    //DB 호출 세 번 해야함 -> DB가 멀리 있으면 오래 걸림!!
    @Test
    public void testDelete2() {

        Long pno = 1L;

        Product product = productRepository.findById(pno).orElseThrow();

        product.changeDelFlag(false);

        productRepository.save(product);
    }

    @Test
    public void testUpdate() {

        Product product = productRepository.selectOne(1L).get();

        product.changePrice(3000);

        product.clearList();

        product.addImageString(UUID.randomUUID() + "_" + "PImage1.jpg");

        product.addImageString(UUID.randomUUID() + "_" + "PImage2.jpg");

        product.addImageString(UUID.randomUUID() + "_" + "PImage3.jpg");

        productRepository.save(product);

    }

    @Test
    public void testList() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));

    }

    @Test
    public void testSearch() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        productRepository.searchList(pageRequestDTO);

    }

}
