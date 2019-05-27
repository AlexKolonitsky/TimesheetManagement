package app.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPageEditDao<T> {

    T getPageData(int id);
}
