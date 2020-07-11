package guru.springframework.msscbeerservice.listener;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.ValidateOrderRequest;
import guru.sfg.brewery.model.events.ValidateOrderResult;
import guru.springframework.msscbeerservice.services.order.BeerOrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static guru.springframework.msscbeerservice.config.JmsConfig.VALIDATE_ORDER_QUEUE;
import static guru.springframework.msscbeerservice.config.JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE;

@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderValidator validator;

    @JmsListener(destination = VALIDATE_ORDER_QUEUE)
    public void listenOrderValidation(ValidateOrderRequest validateOrderRequest) {
        BeerOrderDto beerOrderDto = validateOrderRequest.getBeerOrderDto();
        boolean isValid = validator.validateOrder(beerOrderDto);
        jmsTemplate.convertAndSend(VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder().id(beerOrderDto.getId()).isValid(isValid).build());
    }

}
