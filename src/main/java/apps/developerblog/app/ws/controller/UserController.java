package apps.developerblog.app.ws.controller;

import apps.developerblog.app.ws.exception.UserServiceException;
import apps.developerblog.app.ws.model.request.UpdateUserRequestModel;
import apps.developerblog.app.ws.model.request.UserDetailsRequestModel;
import apps.developerblog.app.ws.model.response.UserRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private Map<String, UserRest> users;

    @GetMapping
    public UserRest getUsers(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "limit", defaultValue = "50") Integer limit,
                             @RequestParam(value = "sort", required = false) String sort) {
        UserRest userRest = null;
        if (page.equals(1))
            throw new UserServiceException("Fuck");
        else
            return userRest;
    }

    @GetMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
        if (users.containsKey(userId)) {
            return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {

        String userId = UUID.randomUUID().toString();
        UserRest returnValue = new UserRest(userDetails.getFirstName(), userDetails.getLastName(), userDetails.getEmail(), userId);
        if (users == null) users = new HashMap<>();
        users.put(userId, returnValue);
        return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{userId}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequestModel userDetails) {
        UserRest storedUserDetails = users.get(userId);
        storedUserDetails.setFirstName(userDetails.getFirstName());
        storedUserDetails.setLastName(userDetails.getLastName());
        users.put(userId, storedUserDetails);
        return storedUserDetails;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        users.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
