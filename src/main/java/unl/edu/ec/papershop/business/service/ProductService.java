package unl.edu.ec.papershop.business.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import unl.edu.ec.papershop.business.service.CrudGenericService;
import unl.edu.ec.papershop.domain.Product;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class ProductService {

    public Product save(Product product) {
        return product;
    }

    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    public List<Product> findAll() {
        return createSampleProducts();
    }

    public long countProducts() {
        return createSampleProducts().size();
    }

    private List<Product> createSampleProducts() {
        List<Product> products = new java.util.ArrayList<>();
        
        // Producto 1
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Hoja Bond A4");
        p1.setDescription("Papel bond tamaño A4, 75 gramos");
        p1.setPrice(java.math.BigDecimal.valueOf(2.50));
        p1.setActive(true);
        products.add(p1);
        
        // Producto 2
        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Cartulina Blanca");
        p2.setDescription("Cartulina blanca 200x250mm");
        p2.setPrice(java.math.BigDecimal.valueOf(1.80));
        p2.setActive(true);
        products.add(p2);
        
        // Producto 3
        Product p3 = new Product();
        p3.setId(3L);
        p3.setName("Sobre Manila");
        p3.setDescription("Sobre manila tamaño oficio");
        p3.setPrice(java.math.BigDecimal.valueOf(0.25));
        p3.setActive(true);
        products.add(p3);
        
        // Producto 4
        Product p4 = new Product();
        p4.setId(4L);
        p4.setName("Papel Periódico");
        p4.setDescription("Papel periódico 57x86cm");
        p4.setPrice(java.math.BigDecimal.valueOf(1.20));
        p4.setActive(true);
        products.add(p4);
        
        // Producto 5
        Product p5 = new Product();
        p5.setId(5L);
        p5.setName("Folder Tamaño Carta");
        p5.setDescription("Folder de manila tamaño carta");
        p5.setPrice(java.math.BigDecimal.valueOf(0.80));
        p5.setActive(true);
        products.add(p5);
        
        return products;
    }
}