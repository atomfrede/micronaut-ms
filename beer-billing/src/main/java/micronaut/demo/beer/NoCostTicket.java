package micronaut.demo.beer;

import io.micronaut.http.HttpResponse;
import io.micronaut.retry.annotation.Fallback;
import io.reactivex.Single;
import micronaut.demo.beer.client.TicketControllerClient;

import javax.validation.constraints.NotBlank;

@Fallback
public class NoCostTicket implements TicketControllerClient{
    @Override
    public HttpResponse<BeerItem> addBeerToCustomerBill(BeerItem beer, @NotBlank String customerName) {
        return HttpResponse.ok();
    }

    @Override
    public Single<Ticket> bill(@NotBlank String customerName) {
        return Single.just(new Ticket());
    }

    @Override
    public HttpResponse resetCustomerBill(@NotBlank String customerName) {
        return HttpResponse.ok();
    }
}
