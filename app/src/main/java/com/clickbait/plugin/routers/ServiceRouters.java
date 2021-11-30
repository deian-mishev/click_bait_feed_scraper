package com.clickbait.plugin.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.stereotype.Component;

@Component
public class ServiceRouters {

    @Bean
    @Router(inputChannel = "integration.gateway.split")
    public RecipientListRouter splitterStoreHandler() {
        RecipientListRouter router = new RecipientListRouter();
        router.addRecipient("integration.gateway.store");
        router.addRecipient("integration.gateway.print");
        return router;
    }

    @Bean
    @Router(inputChannel = "integration.gateway.direct.print")
    public RecipientListRouter fromApiPrint() {
        RecipientListRouter router = new RecipientListRouter();
        router.addRecipient("integration.gateway.print.response");
        router.addRecipient("integration.gateway.print");
        return router;
    }

    @Bean
    @Router(inputChannel = "integration.gateway.direct.store.fixed")
    public RecipientListRouter fromApiStore() {
        RecipientListRouter router = new RecipientListRouter();
        router.addRecipient("integration.gateway.store.response");
        router.addRecipient("integration.gateway.store");
        return router;
    }
}
