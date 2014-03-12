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

	private String linkPdf;

	@PostConstruct
	public void init() {
		try {
			ConfigDAO configDao = ConfigDAO.getInstance();

			linkPdf = configDao.value(Config._LINK_PDF);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(ActionEvent actionEvent) {
		try {
			ConfigDAO configDao = ConfigDAO.getInstance();

			configDao.update(configDao.get(Config._LINK_PDF).setValue(linkPdf));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso", "Configurações salvas com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro", e.getMessage()));
		}
	}

	public String getLinkPdf() {
		return linkPdf;
	}

	public void setLinkPdf(String linkPdf) {
		this.linkPdf = linkPdf;
	}

}
