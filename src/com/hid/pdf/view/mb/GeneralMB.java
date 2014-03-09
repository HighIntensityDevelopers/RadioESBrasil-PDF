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
@ManagedBean(name = "general")
public class GeneralMB implements Serializable {
	private static final long serialVersionUID = 1886651681656025326L;

	private String ftp;

	@PostConstruct
	public void init() {
		try {
			ConfigDAO configDao = ConfigDAO.getInstance();

			ftp = configDao.value(Config._FTP);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(ActionEvent actionEvent) {
		try {
			ConfigDAO configDao = ConfigDAO.getInstance();

			configDao.update(configDao.get(Config._FTP).setValue(ftp));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso", "Configurações salvas com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro", e.getMessage()));
		}
	}

	public String getFtp() {
		return ftp;
	}

	public void setFtp(String ftp) {
		this.ftp = ftp;
	}

}
