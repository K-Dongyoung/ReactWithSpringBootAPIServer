package org.zerok.apiserver.repository.search;

import org.springframework.data.domain.Page;
import org.zerok.apiserver.domain.Todo;
import org.zerok.apiserver.dto.PageRequestDTO;

public interface TodoSearch {

    Page<Todo> search1(PageRequestDTO pageRequestDTO);
}
