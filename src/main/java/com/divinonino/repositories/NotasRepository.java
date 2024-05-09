package com.divinonino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.divinonino.models.entities.Notas;

@Repository
public interface NotasRepository extends JpaRepository<Notas, Long> {
    
}
