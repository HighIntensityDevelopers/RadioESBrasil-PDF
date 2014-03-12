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
@ManagedBean(name = "question")
public class QuestionMB implements Serializable {
	private static final long serialVersionUID = 5081881444364980523L;

	private String question1;
	private String question2;
	private String question3;
	private String question4;
	private String question5;

	@PostConstruct
	public void init() {
		try {
			ConfigDAO configDao = ConfigDAO.getInstance();

			question1 = configDao.value(Config._QUESTION_1);
			question2 = configDao.value(Config._QUESTION_2);
			question3 = configDao.value(Config._QUESTION_3);
			question4 = configDao.value(Config._QUESTION_4);
			question5 = configDao.value(Config._QUESTION_5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(ActionEvent actionEvent) {
		try {
			ConfigDAO configDao = ConfigDAO.getInstance();

			configDao.update(configDao.get(Config._QUESTION_1).setValue(question1));
			configDao.update(configDao.get(Config._QUESTION_2).setValue(question2));
			configDao.update(configDao.get(Config._QUESTION_3).setValue(question3));
			configDao.update(configDao.get(Config._QUESTION_4).setValue(question4));
			configDao.update(configDao.get(Config._QUESTION_5).setValue(question5));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso", "Configurações salvas com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro", e.getMessage()));
		}
	}

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(String question3) {
		this.question3 = question3;
	}

	public String getQuestion4() {
		return question4;
	}

	public void setQuestion4(String question4) {
		this.question4 = question4;
	}

	public String getQuestion5() {
		return question5;
	}

	public void setQuestion5(String question5) {
		this.question5 = question5;
	}

}
