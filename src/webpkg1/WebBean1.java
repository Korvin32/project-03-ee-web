package webpkg1;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import session.ProductFacade;
import entity.Customer;
import entity.Product;

@ManagedBean
@SessionScoped
public class WebBean1 implements Serializable {
    
	private static final long serialVersionUID = 6946899497510749792L;

	private static final Logger LOG = LoggerFactory.getLogger(WebBean1.class);
	
	@EJB
	private ProductFacade productFacade;
	
	private boolean loggedIn = false;
	
	private Customer loggedinCustomer;
	
	private boolean editCabinet = false;
	
	public WebBean1() {
		LOG.info("Stateless Session Bean 'WebBean1' - constructed!");
	}
	
	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	public void addSimilarProduct() {
		List<Product> products = productFacade.findAll();
		Product currentProduct = products.get(0);
		Product similarProduct = products.get(1);
//		productFacade.addSimilarProduct(currentProduct, similarProduct);
	}
	
	public void addItSelf() {
		Product product = productFacade.find(new Integer(3));
//		productFacade.addSimilarProduct(product, product);
	}
	
	public void action() {
	    LOG.info("action called!");
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.loggedIn = isLoggedIn;
	}

	public Customer getLoggedinCustomer() {
		return loggedinCustomer;
	}

	public void setLoggedinCustomer(Customer loggedinCustomer) {
		this.loggedinCustomer = loggedinCustomer;
	}
	
	public boolean isEditCabinet() {
		return editCabinet;
	}

	public void setEditCabinet(boolean editCabinet) {
		this.editCabinet = editCabinet;
	}

}
