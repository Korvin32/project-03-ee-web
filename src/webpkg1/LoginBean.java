package webpkg1;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import session.CustomerManager;
import entity.Customer;

@ManagedBean(name="login")
//@RequestScoped
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -1076990188239204819L;

	@EJB
	private CustomerManager customerManager;
	
	@ManagedProperty(value="#{webBean1}")
	private WebBean1 webBean1;

	private HttpSession session;
	
	private String login;
	private String password;
	
	public LoginBean() {
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	public String doLogin() {
		Customer customer;
		try {
			customer = customerManager.checkLoginData(login, password);
			if (customer != null) {
				session.setAttribute("customer", customer);
				
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.addCookie(new Cookie("login", customer.getLogin()));
				response.addCookie(new Cookie("password", customer.getPassword()));
				
				webBean1.setLoggedIn(true);
				webBean1.setLoggedinCustomer(customer);
				return "index";
			}
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown authentication error occurs! Please try again", null));
            return null;
		} catch (Exception e) {
			System.out.println("[LoginBean] - ERROR - Exception: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return null;
		}
		
	}

	public void doEdit() {
		webBean1.setEditCabinet(true);
	}
	
	public void doCancel() {
		webBean1.setEditCabinet(false);
	}
	
	public void doSave() {
		try {
			Customer customer = (Customer) session.getAttribute("customer");
			Customer newCustomer = customerManager.updateCustomerData(customer);
			
			session.setAttribute("customer", newCustomer);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.addCookie(new Cookie("login", newCustomer.getLogin()));
			response.addCookie(new Cookie("password", newCustomer.getPassword()));
			
			webBean1.setEditCabinet(false);
		} catch (Exception e) {
			System.out.println("[LoginBean] - ERROR - Exception: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}
	
	public WebBean1 getWebBean1() {
		return webBean1;
	}

	public void setWebBean1(WebBean1 webBean1) {
		this.webBean1 = webBean1;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
