package car_rental.api.client;


import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createOrUpdateClient(Client client){
        return clientRepository.save(client);
    }

    public List<Client> getAllClient(){
        return clientRepository.findAll();
    }

    public Client getClientById(long id){
        return clientRepository.findById(id).orElse(null);
    }

    @Transactional
    public int deleteClientById(long id){
        return clientRepository.deleteClientById(id);
    }

}
