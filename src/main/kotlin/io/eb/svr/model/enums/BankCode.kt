package io.eb.svr.model.enums

import lombok.Data

/**
 * Create by lucy on 2019-03-14
 **/
@Data
enum class BankCode (var type: String, var desc: String) {
	GNB("GNB", "경남은행"),
	GJB("GJB", "광주은행"),
	KMB("KMB", "국민은행"),
	IBB("IBB", "기업은행"),
	NH("NH", "농협은행"),
	DGB("DGB", "대구은행"),
	BSB("BSB", "부산은행"),
	KDB("KDB", "산업은행"),
	FSB("FSB", "상호저축은행"),
	MGB("MGB", "새마을금고"),
	SH("SH", "수협"),
	SHB("SHB" , "신한은행"),
	CU("CU", "신용협동조합"),
	CTB("CTB", "씨티은행"),
	KEB("KEB", "KEB하나은행"),
	WRB("WRB", "우리은행"),
	EPB("EPB", "우체국"),
	JBB("JBB", "전북은행"),
	JJB("JJB", "제주은행"),
	SCB("SCB", "SC제일은행"),
	DSB("DSB", "도이치은행"),
	KAB("KAB", "카카오뱅크"),
	KKB("KKB", "K뱅크")
	;
}