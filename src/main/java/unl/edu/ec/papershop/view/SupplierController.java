package unl.edu.ec.papershop.view;

import unl.edu.ec.papershop.business.service.SupplierService;
import unl.edu.ec.papershop.domain.Supplier;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Named("supplierController")
@ViewScoped
public class SupplierController implements Serializable {

    private List<Supplier> suppliers;
    private Supplier selectedSupplier;
    private String searchText;

    @Inject
    private SupplierService supplierService;

@PostConstruct
    public void init() {
        suppliers = supplierService.findAll();
        // Si no hay proveedores, crear datos de ejemplo
        if (suppliers == null || suppliers.isEmpty()) {
            createSampleSuppliers();
        }
    }

    private void createSampleSuppliers() {
        suppliers = new ArrayList<>();
        
        Supplier supplier1 = new Supplier();
        supplier1.setId(1L);
        supplier1.setName("Papelería Central");
        supplier1.setEmail("central@papeleria.com");
        supplier1.setPhone("+593-2-2345678");
        suppliers.add(supplier1);

        Supplier supplier2 = new Supplier();
        supplier2.setId(2L);
        supplier2.setName("Importadora del Sur");
        supplier2.setEmail("importadora@sur.com");
        supplier2.setPhone("+593-2-3456789");
        suppliers.add(supplier2);

        Supplier supplier3 = new Supplier();
        supplier3.setId(3L);
        supplier3.setName("Distribuidora Nacional");
        supplier3.setEmail("distri@nacional.com");
        supplier3.setPhone("+593-2-4567890");
        suppliers.add(supplier3);
    }

    /* ===== NUEVO ===== */
    public void newSupplier() {
        selectedSupplier = new Supplier();
        System.out.println("000000000000000000 Selected Supplier - " + selectedSupplier);
    }

    /* ===== EDITAR ===== */
    public void editSupplier(Supplier supplier) {
        selectedSupplier = supplier;
    }

    /* ===== GUARDAR ===== */
    public void save() {
        System.out.println("Ingreso a gaurdar " + selectedSupplier);
        supplierService.save(selectedSupplier);
        suppliers = supplierService.findAll();
    }

    /* ===== ELIMINAR ===== */
    public void deleteSupplier(Supplier supplier) {
        supplierService.delete(supplier);
        suppliers = supplierService.findAll();
    }

    /* ===== BUSCAR ===== */
    public void search() {
        suppliers = supplierService.search(searchText);
    }

    /* ===== GETTERS & SETTERS ===== */

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public Supplier getSelectedSupplier() {
        return selectedSupplier;
    }

    public void setSelectedSupplier(Supplier selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
