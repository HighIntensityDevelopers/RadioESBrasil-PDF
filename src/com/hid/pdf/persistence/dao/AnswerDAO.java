package com.hid.pdf.persistence.dao;

import com.hid.pdf.domain.Answer;

@DatabaseTable("Answer")
@EntityClass(Answer.class)
public class AnswerDAO extends DAO<Answer> {
	private static AnswerDAO dao;

	public static AnswerDAO getInstance() {
		if (dao == null) {
			dao = new AnswerDAO();
		}

		return dao;
	}

}
