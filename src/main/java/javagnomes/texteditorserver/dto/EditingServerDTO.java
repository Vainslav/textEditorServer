package javagnomes.texteditorserver.dto;

import javagnomes.texteditorserver.entities.EditingServerEntity;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EditingServerDTO implements Serializable {
    private Long id;
    private String serverName;

    public EditingServerDTO(EditingServerEntity entity) {
        this.id = entity.getId();
        this.serverName = entity.getServerName();
    }
}
