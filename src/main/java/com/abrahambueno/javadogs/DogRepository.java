package com.abrahambueno.javadogs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dogs, Long> {
}
