package webpkg1;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import session.ProductFacade;
import entity.Product;

@ManagedBean
@SessionScoped
public class ProductsStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ProductFacade productFacade;
    
    public List<Product> getProducts() {
        return productFacade.findAll();
    }

}
