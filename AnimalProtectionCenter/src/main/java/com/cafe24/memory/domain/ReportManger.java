package com.cafe24.memory.domain;

public class ReportManger {
	private String acceptCode;
	private SearchReportAnimal searchReport;
	private LostReportAnimal lostReport;
	private String acceptState;
	private Member member;
	private String acceptInDate;
	private String acceptOutDate;
	public LostReportAnimal getLostReportCode() {
		return lostReport;
	}
	public void setLostReportCode(LostReportAnimal lostReport) {
		this.lostReport = lostReport;
	}
	public String getAcceptCode() {
		return acceptCode;
	}
	public void setAcceptCode(String acceptCode) {
		this.acceptCode = acceptCode;
	}
	public SearchReportAnimal getReportCode() {
		return searchReport;
	}
	public void setReportCode(SearchReportAnimal searchReport) {
		this.searchReport = searchReport;
	}
	public String getAcceptState() {
		return acceptState;
	}
	public void setAcceptState(String acceptState) {
		this.acceptState = acceptState;
	}
	public Member getMemberId() {
		return member;
	}
	public void setMemberId(Member member) {
		this.member = member;
	}
	public String getAcceptInDate() {
		return acceptInDate;
	}
	public void setAcceptInDate(String acceptInDate) {
		this.acceptInDate = acceptInDate;
	}
	public String getAcceptOutDate() {
		return acceptOutDate;
	}
	public void setAcceptOutDate(String acceptOutDate) {
		this.acceptOutDate = acceptOutDate;
	}
	@Override
	public String toString() {
		return "ReportManger [acceptCode=" + acceptCode + ", searchReport=" + searchReport + ", lostReport="
				+ lostReport + ", acceptState=" + acceptState + ", member=" + member + ", acceptInDate="
				+ acceptInDate + ", acceptOutDate=" + acceptOutDate + "]";
	}
	
	
	
}
