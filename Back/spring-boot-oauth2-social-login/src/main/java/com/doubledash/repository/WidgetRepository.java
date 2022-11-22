package com.doubledash.repository;

import com.doubledash.model.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WidgetRepository extends JpaRepository<Widget, Long> {
    Optional<Widget> findById(Long id);
}
