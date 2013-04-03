package webpkg1;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import session.ProductFacade;
import entity.Customer;
import entity.Product;

@ManagedBean
@SessionScoped
public class WebBean1 implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProductFacade productFacade;
	
	private boolean loggedIn = false;
	
	private Customer loggedinCustomer;
	
	public WebBean1() {
		System.out.println("Stateless Session Bean 'WebBean1' - constructed!");
	}
	
	@PostConstruct
	public void init() {
		System.out.println("[WebBean1] - init()");
		Product product;
		List<Product> products = productFacade.findAll();
		if (products == null || products.isEmpty()) {
			for (int i = 0; i < 10; i++) {
				product = new Product("Prod" + i, "descr" + i);
				productFacade.create(product);
			}
		}
	}
	
	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	public void addSimilarProduct() {
		List<Product> products = productFacade.findAll();
		Product currentProduct = products.get(0);
		Product similarProduct = products.get(1);
		productFacade.addSimilarProduct(currentProduct, similarProduct);
	}
	
	public void addItSelf() {
		Product product = productFacade.find(new Integer(3));
		productFacade.addSimilarProduct(product, product);
	}
	
	public void action() {
		System.out.println("action called!");
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
	
}
