package Vitor.alex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Vitor.alex.modelo.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	Aluno findByLogin(String login);
}
