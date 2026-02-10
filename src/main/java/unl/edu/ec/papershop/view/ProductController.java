package unl.edu.ec.papershop.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.papershop.business.service.ProductService;
import unl.edu.ec.papershop.domain.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ProductController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductService productService;

    private Product selectedProduct;
    private List<Product> products;
    private String filterName;

    public ProductController() {
        this.selectedProduct = new Product();
        this.products = new ArrayList<>();
    }

    public void loadProducts() {
        this.products = productService.findAll();
    }

    public void createProduct() {
        try {
            if (selectedProduct.getName() == null || selectedProduct.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
            }

            if (selectedProduct.getPrice() == null || selectedProduct.getPrice().doubleValue() <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor a cero");
            }

            // Generar ID para el nuevo producto
            long newId = products.stream()
                    .mapToLong(p -> p.getId() != null ? p.getId() : 0)
                    .max()
                    .orElse(0) + 1;
            selectedProduct.setId(newId);
            selectedProduct.setActive(true);

            products.add(selectedProduct);
            resetSelectedProduct();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct() {
        try {
            if (selectedProduct.getId() == null) {
                throw new IllegalArgumentException("El ID del producto no puede ser nulo para actualizar");
            }

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId().equals(selectedProduct.getId())) {
                    products.set(i, selectedProduct);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Long productId) {
        try {
            products.removeIf(product -> product.getId().equals(productId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectProduct(Product product) {
        this.selectedProduct = product;
    }

    public void resetSelectedProduct() {
        this.selectedProduct = new Product();
        this.selectedProduct.setPrice(java.math.BigDecimal.ZERO);
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

    public void filterProducts() {
        if (filterName != null && !filterName.trim().isEmpty()) {
            products = productService.findAll().stream()
                    .filter(p -> p.getName().toLowerCase().contains(filterName.toLowerCase()))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } else {
            loadProducts();
        }
    }

    public void clearFilters() {
        this.filterName = null;
        loadProducts();
    }

    public List<Product> getProducts() {
        if (products == null || products.isEmpty()) {
            loadProducts();
        }
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public boolean isEditing() {
        return selectedProduct != null && selectedProduct.getId() != null;
    }

    public long getProductCount() {
        return productService.countProducts();
    }
}