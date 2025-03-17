package javagnomes.texteditorserver.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class EditingServerHostDTO implements Serializable {
    private String serverName;

    private String description;

    private boolean isPublic;

    private String password;
}
