package guru.springframework.msscbeerservice.services.order;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public Boolean validateOrder(BeerOrderDto beerOrder) {
        boolean isValid = beerOrder.getBeerOrderLines().stream()
                .map(orderLine -> beerRepository.findByUpc(orderLine.getUpc()))
                .noneMatch(Objects::isNull);
        log.debug("Beer order [{}] isValid: {}", beerOrder.getId(), isValid);
        return isValid;

        // Original logic
//        AtomicInteger beersNotFound = new AtomicInteger();
//        beerOrder.getBeerOrderLines().forEach(orderLine -> {
//            if (beerRepository.findByUpc(orderLine.getUpc()) == null) {
//                beersNotFound.incrementAndGet();
//            }
//        });
//        log.info("beersNotFound.get(): {}", beersNotFound.get());
//        return beersNotFound.get() == 0;
    }

}
