package agrosmart.agrosmart_api.repository;

import agrosmart.agrosmart_api.model.PasswordResetToken;
import agrosmart.agrosmart_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(User user);
    void deleteByUser(User user);
}