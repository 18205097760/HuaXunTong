package com.nju.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChemBasic {
	private int id;
	private String chem;
	private String name;
	private String othername;
	private String cas;
	private String molfor;
	private String molwei;
	private String physical;
	private String health;
	private String environment;
	private String msgword;
	private String ref;
	private String pics;

	private JSONArray physicals;
	private JSONArray healths;
	private JSONArray environments;
	
	public int getId() {
		return id;
	}
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChem() {
		return chem;
	}
	public void setChem(String chem) {
		this.chem = chem;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOthername() {
		return othername;
	}
	public void setOthername(String othername) {
		if(othername != null){
			othername = othername.replaceAll("\n", "");
		}
		this.othername = othername;
	}
	public String getCas() {
		return cas;
	}
	public void setCas(String cas) {
		this.cas = cas;
	}
	public String getMolfor() {
		return molfor;
	}
	public void setMolfor(String molfor) {
		this.molfor = molfor;
	}
	public String getMolwei() {
		return molwei;
	}
	public void setMolwei(String molwei) {
		this.molwei = molwei;
	}
	public String getPhysical() {
		return physical;
	}
	public void setPhysical(String physical) {
		this.physical = physical;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getMsgword() {
		return msgword;
	}
	public void setMsgword(String msgword) {
		this.msgword = msgword;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Override
	public String toString() {
		return "ChemBasic [id=" + id + ", chem=" + chem + ", name=" + name
				+ ", othername=" + othername + ", cas=" + cas + ", molfor="
				+ molfor + ", molwei=" + molwei + ", physical=" + physical
				+ ", health=" + health + ", environment=" + environment
				+ ", msgword=" + msgword + ", ref=" + ref + "]";
	}
	
	public JSONObject toJsonObject() {
		JSONObject chemBasic = new JSONObject();
		try {
			chemBasic.accumulate("id", id);
			chemBasic.accumulate("chem", chem);
			chemBasic.accumulate("name", name);
			chemBasic.accumulate("othername", othername);
			chemBasic.accumulate("cas", cas);
			chemBasic.accumulate("molfor", molfor);
			chemBasic.accumulate("molwei", molwei);
			chemBasic.accumulate("physicals", physicals);
			chemBasic.accumulate("healths", healths);
			chemBasic.accumulate("environments", environments);
			chemBasic.accumulate("msgword", msgword);
			chemBasic.accumulate("ref", ref);
			chemBasic.accumulate("pics", pics);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chemBasic;
	}
	public JSONArray getPhysicals() {
		return physicals;
	}
	public void setPhysicals(JSONArray physicals) {
		this.physicals = physicals;
	}
	public JSONArray getHealths() {
		return healths;
	}
	public void setHealths(JSONArray healths) {
		this.healths = healths;
	}
	public JSONArray getEnvironments() {
		return environments;
	}
	public void setEnvironments(JSONArray environments) {
		this.environments = environments;
	}
	
}
