package pl.bookworm.bookworm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.bookworm.bookworm.model.ConfirmationCode;

@Repository
public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long>{
	ConfirmationCode findByCode(String codeString);
}
