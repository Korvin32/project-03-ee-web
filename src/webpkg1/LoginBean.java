package webpkg1;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import session.CustomerManager;
import entity.Customer;

@ManagedBean(name="login")
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CustomerManager customerManager;
	
	private HttpSession session;
	
	@ManagedProperty(value="#{webBean1}")
	private WebBean1 webBean1;

	private String login;
	private String password;
	
	public LoginBean() {
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	public String doLogin() {
		try {
			Customer customer = customerManager.checkLoginData(login, password);
			if (customer != null) {
				session.setAttribute("customer", customer);
				webBean1.setLoggedIn(true);
				webBean1.setLoggedinCustomer(customer);
				return "index";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No such User or wrong password entered! Please try again", null));
				return null;
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return null;
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
