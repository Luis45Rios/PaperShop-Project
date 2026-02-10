package unl.edu.ec.papershop.business;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import unl.edu.ec.papershop.business.service.ProductService;
import unl.edu.ec.papershop.domain.Product;
import unl.edu.ec.papershop.domain.Report;
import unl.edu.ec.papershop.exception.EntityNotFoundException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PapershopFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductService productService;

    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    public Product createProduct(Product product) {
        return productService.save(product);
    }
}
