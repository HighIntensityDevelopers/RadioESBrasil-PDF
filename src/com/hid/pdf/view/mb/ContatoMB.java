package com.hid.pdf.view.mb;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hid.pdf.domain.Answer;
import com.hid.pdf.domain.Config;
import com.hid.pdf.persistence.dao.AnswerDAO;
import com.hid.pdf.persistence.dao.ConfigDAO;

@SessionScoped
@ManagedBean(name = "contato")
public class ContatoMB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8598348121L;
	private String email;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String answer5;
	private String question1;
	private String question2;
	private String question3;
	private String question4;
	private String question5;
	private String msgDownload;
	private String linkPDF;

	public String getMsgDownload() {
		return msgDownload;
	}

	public void setMsgDownload(String msgDownload) {
		this.msgDownload = msgDownload;
	}

	public String getLinkPDF() {
		return linkPDF;
	}

	public void setLinkPDF(String linkPDF) {
		this.linkPDF = linkPDF;
	}


	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getAnswer5() {
		return answer5;
	}

	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@PostConstruct
	public void init() {
		question1 = ConfigDAO.getInstance().get(Config._QUESTION_1).getValue();
		question2 = ConfigDAO.getInstance().get(Config._QUESTION_2).getValue();
		question3 = ConfigDAO.getInstance().get(Config._QUESTION_3).getValue();
		question4 = ConfigDAO.getInstance().get(Config._QUESTION_4).getValue();
		question5 = ConfigDAO.getInstance().get(Config._QUESTION_5).getValue();
		msgDownload =ConfigDAO.getInstance().get(Config._MSGDONWLOAD).getValue() ; 
		linkPDF =ConfigDAO.getInstance().get(Config._LINK_PDF).getValue() ;
		System.out.println("MSG_DOWNLOAD"+msgDownload);
	}

	public void salvarContato() {
		Answer answer = new Answer();
		try {

			answer.setEmail(email);
			answer.setQuestion1(question1);
			answer.setAnswer1(answer1);
			answer.setQuestion2(question2);
			answer.setAnswer2(answer2);
			answer.setQuestion3(question3);
			answer.setAnswer3(answer3);
			answer.setQuestion4(question4);
			answer.setAnswer4(answer4);
			answer.setQuestion5(question5);
			answer.setAnswer5(answer5);
			AnswerDAO.getInstance().create(answer);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();
			facesContext.getExternalContext().redirect(
					request.getContextPath() + "/pages/download.jsf");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setFirstPage() {
		this.setEmail(email);
		// HttpServletResponse response = (HttpServletResponse) FacesContext
		// .getCurrentInstance().getExternalContext().getResponse();
		// try {
		// response.sendRedirect("pages/complet.xhtml");
		// FacesContext.getCurrentInstance().responseComplete();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();
			facesContext.getExternalContext().redirect(
					request.getContextPath() + "/pages/contato.jsf");
			question1 = ConfigDAO.getInstance().get(Config._QUESTION_1)
					.getValue();

			// request.getSession().setAttribute("session-user", new
			// SessionUser());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
