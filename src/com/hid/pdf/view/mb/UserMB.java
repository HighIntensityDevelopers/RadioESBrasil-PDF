package com.hid.pdf.view.mb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.hid.pdf.domain.Config;
import com.hid.pdf.persistence.dao.ConfigDAO;

@ViewScoped
@ManagedBean(name = "user")
public class UserMB implements Serializable {
	private static final long serialVersionUID = 1886651681656025326L;

	private String u;
	private String p;

	@PostConstruct
	public void init() {
		try {
			ConfigDAO configDao = ConfigDAO.getInstance();

			u = configDao.value(Config._U);
			p = configDao.value(Config._P);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(ActionEvent actionEvent) {
		try {
			ConfigDAO configDao = ConfigDAO.getInstance();

			configDao.update(configDao.get(Config._U).setValue(u));
			configDao.update(configDao.get(Config._P).setValue(p));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso", "Configurações salvas com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro", e.getMessage()));
		}
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

}
