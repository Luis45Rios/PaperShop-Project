package unl.edu.ec.papershop.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.papershop.business.PapershopFacade;
import unl.edu.ec.papershop.domain.Product;
import unl.edu.ec.papershop.view.security.UserSession;

import java.util.List;

@Named
@ViewScoped
public class DashboardController implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    @Inject
    private UserSession userSession;

    @Inject
    private PapershopFacade  papershopFacade;


    public DashboardController(){
    }

    public List<Product> getAllProducts(){
        return papershopFacade.getAllProducts();
    }


}
