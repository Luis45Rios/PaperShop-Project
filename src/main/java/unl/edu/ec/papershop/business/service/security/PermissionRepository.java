package unl.edu.ec.papershop.business.service.security;

import jakarta.ejb.Stateless;
import unl.edu.ec.papershop.domain.security.ActionType;
import unl.edu.ec.papershop.domain.security.Permission;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Stateless
public class PermissionRepository {

    private static final Map<Long, Permission> tablePermissionBD;

    static {
        tablePermissionBD = new TreeMap<>();

        // ========== PERMISOS DE SEGURIDAD Y USUARIOS ==========
        tablePermissionBD.put(1L, new Permission(1L, "/security/userList.xhtml", ActionType.ALL));
        tablePermissionBD.put(2L, new Permission(2L, "/security/userEdit.xhtml", ActionType.READ));
        tablePermissionBD.put(3L, new Permission(3L, "/security/userEdit.xhtml", ActionType.WRITE));
        tablePermissionBD.put(4L, new Permission(4L, "/security/userList.xhtml", ActionType.DELETE));

        // ========== PERMISOS DE PRODUCTOS ==========
        tablePermissionBD.put(5L, new Permission(5L, "/products/productList.xhtml", ActionType.ALL));
        tablePermissionBD.put(6L, new Permission(6L, "/products/productEdit.xhtml", ActionType.READ));
        tablePermissionBD.put(7L, new Permission(7L, "/products/productEdit.xhtml", ActionType.WRITE));
        tablePermissionBD.put(8L, new Permission(8L, "/products/productList.xhtml", ActionType.DELETE));

        // ========== PERMISOS DE PROVEEDORES ==========
        tablePermissionBD.put(9L, new Permission(9L, "/products/supplierList.xhtml", ActionType.ALL));
        tablePermissionBD.put(10L, new Permission(10L, "/products/supplierEdit.xhtml", ActionType.READ));
        tablePermissionBD.put(11L, new Permission(11L, "/products/supplierEdit.xhtml", ActionType.WRITE));
        tablePermissionBD.put(12L, new Permission(12L, "/products/supplierList.xhtml", ActionType.DELETE));

        // ========== PERMISOS DE VENTAS ==========
        tablePermissionBD.put(13L, new Permission(13L, "/sales/newSale.xhtml", ActionType.READ));
        tablePermissionBD.put(14L, new Permission(14L, "/sales/newSale.xhtml", ActionType.WRITE));
        tablePermissionBD.put(15L, new Permission(15L, "/sales/saleHistory.xhtml", ActionType.READ));
        tablePermissionBD.put(16L, new Permission(16L, "/sales/clientList.xhtml", ActionType.READ));
        tablePermissionBD.put(17L, new Permission(17L, "/sales/clientEdit.xhtml", ActionType.WRITE));

        // ========== PERMISOS DE INVENTARIO ==========
        tablePermissionBD.put(18L, new Permission(18L, "/inventory/inventoryList.xhtml", ActionType.READ));
        tablePermissionBD.put(19L, new Permission(19L, "/inventory/inventoryList.xhtml", ActionType.WRITE));
        tablePermissionBD.put(20L, new Permission(20L, "/inventory/purchaseList.xhtml", ActionType.READ));
        tablePermissionBD.put(21L, new Permission(21L, "/inventory/purchaseEdit.xhtml", ActionType.WRITE));

        // ========== PERMISOS DE REPORTES ==========
        tablePermissionBD.put(22L, new Permission(22L, "/reports/salesReport.xhtml", ActionType.READ));
        tablePermissionBD.put(23L, new Permission(23L, "/reports/inventoryReport.xhtml", ActionType.READ));
        tablePermissionBD.put(24L, new Permission(24L, "/reports/financialReport.xhtml", ActionType.READ));

        // ========== PERMISOS GENERALES ==========
        tablePermissionBD.put(25L, new Permission(25L, "/dashboard.xhtml", ActionType.READ));
        tablePermissionBD.put(26L, new Permission(26L, "/sales/printInvoice.xhtml", ActionType.READ));
        tablePermissionBD.put(27L, new Permission(27L, "/sales/printInvoice.xhtml", ActionType.WRITE));
        tablePermissionBD.put(28L, new Permission(28L, "/sales/searchProduct.xhtml", ActionType.READ));
        tablePermissionBD.put(29L, new Permission(29L, "/products/priceCalculator.xhtml", ActionType.WRITE));
        tablePermissionBD.put(30L, new Permission(30L, "/inventory/weightedAverage.xhtml", ActionType.WRITE));
    }

    public List<Permission> findAll() {
        return List.copyOf(tablePermissionBD.values());
    }

    public Permission find(Long id) {
        return tablePermissionBD.get(id);
    }
}