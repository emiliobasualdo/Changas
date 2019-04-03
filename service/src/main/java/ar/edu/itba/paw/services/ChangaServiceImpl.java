package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ChangaService;
import ar.edu.itba.paw.models.Changa;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class ChangaServiceImpl implements ChangaService {

    private static final int N_CHANGAS = 100;

    @Override
    public List<Changa> getChangas() {
        return randomChangas();
    }

    @Override
    public Changa getById(long id) {
        return new Changa("Sofi Murandi", "1133071114", "Lavar el perro", "Hay que hacerlo la palo", 32423.0, "Martinez");
    }

    private List<Changa> randomChangas() {
        String[] ownerName = {"Juan Carlos", "Camila", "Sofi Murandi", "Rodrigo Escarapietra", "Carmen Villaurquiza"};
        String[] ownerPhone = {"1133071114", "1133071114", "1133071114", "1133071114", "1133071114",};
        String[] title = {"Lavar el perro", "Lavar los platos", "Lavarme el culo", "Prositushon", "Se busca nene de 5 años",};
        String[] description = {"Hay que hacerlo la palo", "Vigorosooo", "Full energetic", "Dale candela", "Muevete",};
        double[] price = {13123, 123, 312, 1, 231};
        String[] neighborhood = {"San Telmo", "Flores", "Talar del cheto", "Quinta presidencial", "Calle 13"};
        Random r = new Random();
        int max = 5;
        List<Changa> resp = new ArrayList<>();
        for (int i = 0; i < N_CHANGAS; i++) {
            resp.add(new Changa(
                    ownerName[r.nextInt(max)],
                    ownerPhone[r.nextInt(max)],
                    title[r.nextInt(max)],
                    description[r.nextInt(max)],
                    price[r.nextInt(max)],
                    neighborhood[r.nextInt(max)]
            ));
        }
        return resp;
    }
}
