package org.zerok.apiserver.repository.search;

import org.springframework.data.domain.Page;
import org.zerok.apiserver.domain.Todo;

public interface TodoSearch {

    Page<Todo> search1();
}
