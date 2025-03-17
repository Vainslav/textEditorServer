package javagnomes.texteditorserver.dto;

import javagnomes.texteditorserver.entities.EditingServerEntity;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EditingServerDTO implements Serializable {
    private Long id;

    private String serverName;

    private String description;

    private boolean isPublic;

    private String host;

    public EditingServerDTO(EditingServerEntity entity) {
        this.id = entity.getId();
        this.serverName = entity.getServerName();
        this.description = entity.getDescription();
        this.isPublic = entity.isPublic();
        this.host = entity.getHost();
    }
}
