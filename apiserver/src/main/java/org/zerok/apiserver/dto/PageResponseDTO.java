package org.zerok.apiserver.dto;

import java.util.List;

public class PageResponseDTO<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long total) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)total;

        int start = pageRequestDTO.getPage() / 10 * 10 + 1;
        int end = start + 9;

        int realEnd = (int)Math.ceil(totalCount / 10.0);
    }
}
