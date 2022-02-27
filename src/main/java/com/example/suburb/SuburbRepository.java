package com.example.suburb;

import com.example.suburb.model.Suburb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuburbRepository extends JpaRepository<Suburb, Integer> {
    Optional<List<Suburb>> findAllByPostcode(int postcode);
}
