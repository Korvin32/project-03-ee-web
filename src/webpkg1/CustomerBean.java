package webpkg1;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import session.CustomerManager;
import entity.Address;
import entity.Customer;

@ManagedBean(name="customer")
@RequestScoped
public class CustomerBean {
	
	@EJB
	private CustomerManager customerManager;

	private HttpSession session;
	
	@ManagedProperty(value="#{webBean1}")
	private WebBean1 webBean1;
	
	private String rePassword;
	private Customer customer;
	private Address address;
	
	public CustomerBean() {
		customer = new Customer();
		address = new Address();
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	public String register() {
		if (!customer.getPassword().equals(rePassword)) {
			return null;
		}
		int customerId = customerManager.registerCustomer(customer, address);
		if (customerId > 0) {
			session.setAttribute("customer", customer);
			return "index";
		} else {
			return "error";
		}
	}

	public WebBean1 getWebBean1() {
		return webBean1;
	}

	public void setWebBean1(WebBean1 webBean1) {
		this.webBean1 = webBean1;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
