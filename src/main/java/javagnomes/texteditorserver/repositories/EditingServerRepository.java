package javagnomes.texteditorserver.repositories;

import javagnomes.texteditorserver.entities.EditingServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditingServerRepository extends JpaRepository<EditingServerEntity, Long>{
    EditingServerEntity findByServerName(String serverName);
}
