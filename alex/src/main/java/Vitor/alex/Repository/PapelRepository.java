package Vitor.alex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Vitor.alex.modelo.Papel;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {
	Papel findByPapel(String papel);

}
