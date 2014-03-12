package com.hid.pdf.view.mb;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import com.hid.pdf.domain.Config;
import com.hid.pdf.domain.SessionUser;
import com.hid.pdf.persistence.dao.ConfigDAO;

@RequestScoped
@ManagedBean(name = "login")
public class LoginMB implements Serializable {
	private static final long serialVersionUID = -7759377011703035L;

	private String username;

	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void login(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;
		boolean loggedIn = false;

		ConfigDAO configDao = ConfigDAO.getInstance();

		if (username != null && username.equals(configDao.get(Config._U).getValue()) && password != null
				&& password.equals(configDao.get(Config._P).getValue())) {
			loggedIn = true;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem vindo", username);

			try {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
				facesContext.getExternalContext().redirect(request.getContextPath() + "/administrator/general.jsf");

				request.getSession().setAttribute("session-user", new SessionUser());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			loggedIn = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", "Credenciais inválidas");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("loggedIn", loggedIn);
	}
}
