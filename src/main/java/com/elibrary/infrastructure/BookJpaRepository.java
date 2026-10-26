package com.elibrary.infrastructure;

import com.elibrary.domain.book.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<BookEntity, String> {
    @Query("select b from BookEntity b where (:type is null or b.contentType = :type) and ("
            + ":keyword is null or lower(b.title) like lower(concat('%', :keyword, '%')) "
            + "or lower(b.author) like lower(concat('%', :keyword, '%')))")
    Page<BookEntity> search(@Param("keyword") String keyword, @Param("type") ContentType type, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from BookEntity b where b.id = :id")
    Optional<BookEntity> lockById(@Param("id") String id);
}
