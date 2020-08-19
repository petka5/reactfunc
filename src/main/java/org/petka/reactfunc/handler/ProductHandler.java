package org.petka.reactfunc.handler;

import org.petka.reactfunc.ProductRepository;
import org.petka.reactfunc.persistence.entity.Product;
import org.petka.reactfunc.persistence.entity.ProductEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ProductHandler {
    private ProductRepository productRepository;

    public ProductHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        Flux<Product> products = productRepository.findAll();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products, Product.class);
    }

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Product> productMono = productRepository.findById(id);

        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        return productMono.flatMap(product ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(product)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        Mono<Product> productMono = request.bodyToMono(Product.class);

        return productMono.flatMap(product -> ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productRepository.save(product), Product.class));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Product> existingProductMono = productRepository.findById(id);
        Mono<Product> productMono = request.bodyToMono(Product.class);

        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return productMono.zipWith(existingProductMono,
                (product, existingProduct) -> Product.builder().id(existingProduct.getId()).
                        name(product.getName()).price(product.getPrice()).build())
                .flatMap(product -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(productRepository.save(product), Product.class))
                .switchIfEmpty(notFound);

    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Product> productMono = productRepository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return productMono.flatMap(existingProduct -> ServerResponse.ok().build(productRepository.delete(existingProduct)))
                .switchIfEmpty(notFound);

    }

    public Mono<ServerResponse> deleteAllProduct(ServerRequest request) {

        return ServerResponse.ok().build(productRepository.deleteAll());

    }

    public Mono<ServerResponse> getProductEvents(ServerRequest request) {

        Flux<ProductEvent> eventFlux = Flux.interval(Duration.ofSeconds(1))
                .map(val -> ProductEvent.builder().eventId(val).eventType("Product Event").build());

        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(eventFlux, ProductEvent.class);
    }
}
