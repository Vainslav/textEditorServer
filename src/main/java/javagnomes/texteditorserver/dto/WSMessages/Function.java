package javagnomes.texteditorserver.dto.WSMessages;

import lombok.Data;

import java.io.Serializable;

@Data
public class Function implements Serializable {
    private Funcs func;
    private int index;
    private String string;
}
