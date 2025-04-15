package javagnomes.texteditorserver.entities;

import jakarta.persistence.*;
import javagnomes.texteditorserver.dto.EditingServerDTO;
import javagnomes.texteditorserver.dto.EditingServerHostDTO;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "editing_server")
public class EditingServerEntity implements Serializable {
    private @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "editing_server_sequence") Long id;

    private String serverName;

    private String password;

    public EditingServerEntity(EditingServerHostDTO hostDTO) {
        this.serverName = hostDTO.getServerName();
        this.password = hostDTO.getPassword();
    }
}
