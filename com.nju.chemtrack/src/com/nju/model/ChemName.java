package com.nju.model;

import org.json.JSONException;
import org.json.JSONObject;


public class ChemName {
	private int id;
	private String cas;
	private String name;
	private String identification;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCas() {
		return cas;
	}
	public void setCas(String cas) {
		this.cas = cas;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	
	public JSONObject toJsonObject() {
		JSONObject chemName = new JSONObject();
		try {
			chemName.accumulate("id", id);
			chemName.accumulate("cas", cas);
			chemName.accumulate("name", name);
			chemName.accumulate("identification", identification);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chemName;
	}
	@Override
	public String toString() {
		return "ChemName [id=" + id + ", cas=" + cas + ", name=" + name
				+ ", identification=" + identification + "]";
	}
}
