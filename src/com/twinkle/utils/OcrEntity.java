package com.twinkle.utils;

import java.util.List;

public class OcrEntity {

	private Long log_id;

	private int words_result_num;

	private List<Result> words_result;

	

	public Long getLog_id() {
		return log_id;
	}

	public void setLog_id(Long log_id) {
		this.log_id = log_id;
	}

	public Integer getWords_result_num() {
		return words_result_num;
	}

	public void setWords_result_num(Integer words_result_num) {
		this.words_result_num = words_result_num;
	}

	public List<Result> getWords_result() {
		return words_result;
	}

	public void setWords_result(List<Result> words_result) {
		this.words_result = words_result;
	}

	public class Result {

		private String words;

		public String getWords() {
			return words;
		}

		public void setWords(String words) {
			this.words = words;
		}

	}

}
