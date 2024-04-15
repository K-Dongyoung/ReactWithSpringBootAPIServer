package org.zerok.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerok.apiserver.domain.Todo;
import org.zerok.apiserver.repository.search.TodoSearch;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}
