package unl.edu.ec.papershop.view;

import unl.edu.ec.papershop.business.service.SupplierService;
import unl.edu.ec.papershop.domain.Supplier;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

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
