package dev.dawsonvaught.javaredbeltexam2.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.dawsonvaught.javaredbeltexam2.models.Show;

@Repository
public interface ShowRepository extends CrudRepository<Show, Long>{
    ArrayList<Show> findAll();
}