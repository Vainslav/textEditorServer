package javagnomes.texteditorserver.controllers;

import com.auth0.jwt.exceptions.JWTCreationException;
import javagnomes.texteditorserver.authorization.JwtUtil;
import javagnomes.texteditorserver.dto.EditingServerHostDTO;
import javagnomes.texteditorserver.dto.JwtDTO;
import javagnomes.texteditorserver.entities.EditingServerEntity;
import javagnomes.texteditorserver.exseptions.http.IncorrectPasswordException;
import javagnomes.texteditorserver.exseptions.http.ServerAlreadyExists;
import javagnomes.texteditorserver.exseptions.http.ServerNotFound;
import javagnomes.texteditorserver.repositories.EditingServerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class EditingServerController {
    private final EditingServerRepository repository;
    private final JwtUtil jwtUtil;

    public EditingServerController(EditingServerRepository editingServerRepository, JwtUtil jwtUtil) {
        this.repository = editingServerRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/servers")
    public List<EditingServerEntity> getAllServers() {
        return repository.findAll();
    }

    @PostMapping("/servers/{path}")
    public JwtDTO hostServer(@PathVariable String path, @RequestBody EditingServerHostDTO serverHost) {
        if (repository.findByHost(path) != null){
            throw new ServerAlreadyExists(path);
        }

        EditingServerEntity newServer = new EditingServerEntity(serverHost, path);
        newServer = repository.save(newServer);
        final String token;
        try{
            token = jwtUtil.createToken(Collections.singletonList(newServer.getHost()));
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }

        return new JwtDTO(token);
    }

    @GetMapping("/servers/{path}")
    public JwtDTO connectToServer(@PathVariable String path, @RequestParam String pass) {
        EditingServerEntity server = repository.findByHost(path);
        if (server == null){
            throw new ServerNotFound(path);
        }

        if (!pass.equals(server.getPassword())){
            throw new IncorrectPasswordException();
        }

        final String token;
        try{
            token = jwtUtil.createToken(Arrays.asList("/app/"+server.getHost(), "/topic/"+server.getHost()));
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }

        return new JwtDTO(token);
    }
}
