package micronaut.demo.beer;


import static org.junit.Assert.assertEquals;

import io.micronaut.http.client.Client;
import micronaut.demo.beer.client.TicketControllerClient;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpResponse;
import io.micronaut.runtime.server.EmbeddedServer;
import io.reactivex.Single;

import javax.inject.Inject;
import java.util.Timer;

public class TicketControllerTest {
	
	private final String USERNAME="mauricio";
	private final String BEER_NAME="mahou";
	

    private EmbeddedServer server;

    @Client(id="billing")
    @Inject
    private TicketControllerClient client;

    @Before
    public void setup() {
        this.server = ApplicationContext.run(EmbeddedServer.class);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.client = server.getApplicationContext().getBean(TicketControllerClient.class);
        client.resetCustomerBill(USERNAME);
    }
    
    @Test
    public void shouldAddNewBeer() {
    		BeerItem beerItem = new BeerItem(BEER_NAME, BeerItem.Size.MEDIUM);
    		HttpResponse<BeerItem> response = client.addBeerToCustomerBill(beerItem, USERNAME);
            assertEquals(response.body().getName(), BEER_NAME);
    }
    
    @Test
    public void shouldGetTicketWithZeroWhenCustomerDidNotOrderBeers() {
    		Single<Ticket> response = client.bill(USERNAME);
            assertEquals(response.blockingGet().getCost(), 0,0);
    }

    @After
    public void cleanup() {
        this.server.stop();
    }
}