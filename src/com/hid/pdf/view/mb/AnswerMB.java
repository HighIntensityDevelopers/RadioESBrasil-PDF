package com.hid.pdf.view.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.hid.pdf.domain.Answer;
import com.hid.pdf.persistence.dao.AnswerDAO;

@ViewScoped
@ManagedBean(name = "answer")
public class AnswerMB implements Serializable {
	private static final long serialVersionUID = -5650191228964049979L;

	private Answer bean = null;
	private List<Answer> list = null;

	@PostConstruct
	public void init() {
		try {
			list = AnswerDAO.getInstance().findAll("1=1 order by id desc");
			bean = new Answer();
		} catch (Exception e) {
			e.printStackTrace();
			this.list = new ArrayList<Answer>();
		}
	}

	public void createNew(ActionEvent evt) {
		this.bean = new Answer();
	}

	public void remove(ActionEvent evt) {
		try {
			AnswerDAO.getInstance().destroy(bean);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Sucesso", "Resposta removida com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro", e.getMessage()));
		}
		init();
	}

	public List<Answer> getList() {
		return this.list;
	}

	public Answer getBean() {
		return bean;
	}

	public void setBean(Answer musicRequest) {
		this.bean = musicRequest;
	}

}
