package javagnomes.texteditorserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtDTO implements Serializable {
    private String token;
}
