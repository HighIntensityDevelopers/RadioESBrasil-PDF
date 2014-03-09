package com.hid.pdf.view.mb;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@ManagedBean(name = "logoff")
public class LogoffMB implements Serializable {
	private static final long serialVersionUID = -8158033718584763953L;

	private static String message = "Desconectando ...";

	@PostConstruct
	private void init() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
			facesContext.getExternalContext().redirect(request.getContextPath() + "/pages/login.jsf");

			request.getSession().setAttribute("user", null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMessage() {
		return message;
	}
}
