package io.github.hylexus.jt.boot.autoconfigure.dashboard.client.configuration.jt808.reactive;

import io.github.hylexus.jt.boot.autoconfigure.dashboard.client.configuration.props.JtApplicationProps;
import io.github.hylexus.jt.boot.autoconfigure.dashboard.client.impl.factory.Jt808ApplicationFactory;
import io.github.hylexus.jt.dashboard.client.registration.JtApplicationRegistrator;
import io.github.hylexus.jt.dashboard.client.registration.impl.client.Jt808ReactiveJtApplicationClient;
import io.github.hylexus.jt.dashboard.client.registration.impl.registrator.DefaultJtApplicationRegistrator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveJtDashboard808Configuration {
    @Bean
    public JtApplicationRegistrator reactiveJt808ApplicationRegistrator(JtApplicationProps applicationProps) {
        return new DefaultJtApplicationRegistrator(
                new Jt808ApplicationFactory(applicationProps),
                new Jt808ReactiveJtApplicationClient(WebClient.builder().build()),
                applicationProps.getJt808().getServerUrl()
        );
    }
}
