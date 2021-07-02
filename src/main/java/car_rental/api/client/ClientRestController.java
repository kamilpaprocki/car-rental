package car_rental.api.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> createOrUpdateClient(@RequestBody Client client){
        if (client.getId() != null){
            return new ResponseEntity<>(clientService.createOrUpdateClient(client), HttpStatus.OK);
        }
        return new ResponseEntity<>(clientService.createOrUpdateClient(client), HttpStatus.CREATED);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients(){
        List<Client> clients = clientService.getAllClient();
        if(clients.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
            return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@RequestParam long id){
        Client client = clientService.getClientById(id);
        if (client == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @DeleteMapping("/clients/{id}/delete")
    public ResponseEntity<Client> deleteClientById(@RequestParam long id){
        if(clientService.deleteClientById(id) > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
