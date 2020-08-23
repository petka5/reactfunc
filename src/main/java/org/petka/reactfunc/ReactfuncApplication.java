package org.petka.reactfunc;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.petka.reactfunc.handler.ProductHandler;
import org.petka.reactfunc.persistence.entity.Product;
import org.petka.reactfunc.persistence.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactfuncApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactfuncApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ProductRepository repository) {
        return args -> {
            Flux<Product> productFlux = Flux.just(
                    Product.builder().name("Big Latte").price(2.99).build(),
                    Product.builder().name("Big Decaf").price(2.49).build(),
                    Product.builder().name("Green Tea").price(1.99).build())
                    .flatMap(repository::save);

            productFlux
                    .thenMany(repository.findAll())
                    .subscribe(System.out::println
                    );
        };
    }

    @Bean
    RouterFunction<ServerResponse> routes(ProductHandler handler) {
        return route(GET("/products").and(accept(MediaType.APPLICATION_JSON)), handler::getAllProducts)
                .andRoute(POST("/products").and(accept(MediaType.APPLICATION_JSON)), handler::saveProduct)
                .andRoute(DELETE("/products").and(accept(MediaType.APPLICATION_JSON)), handler::deleteAllProduct)
                .andRoute(GET("/products/events").and(accept(MediaType.TEXT_EVENT_STREAM)), handler::getProductEvents)
                .andRoute(GET("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getProduct)
                .andRoute(PUT("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::updateProduct)
                .andRoute(DELETE("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteProduct);

/*        return nest(path("/products"),
                nest(accept(MediaType.APPLICATION_JSON).or(contentType(MediaType.APPLICATION_JSON))
                                .or(accept(MediaType.TEXT_EVENT_STREAM)),
                        route(GET("/"), handler::getAllProducts)
                                .andRoute(method(HttpMethod.POST), handler::saveProduct)
                                .andRoute(DELETE("/"), handler::deleteAllProduct)
                                .andRoute(GET("/events"), handler::getProductEvents)
                                .andNest(path("/{id}"),
                                        route(method(HttpMethod.GET), handler::getProduct)
                                                .andRoute(method(HttpMethod.PUT), handler::updateProduct)
                                                .andRoute(method(HttpMethod.DELETE), handler::deleteProduct)))
        );*/
    }
}
