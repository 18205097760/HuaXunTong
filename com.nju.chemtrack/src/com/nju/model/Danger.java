package com.nju.model;

import org.json.JSONException;
import org.json.JSONObject;



public class Danger {
	private int id;
	private String num;
	private String dangercode;
	private String dangercont;
	private String dangertype;
	private String ghs;
	private String dangerclass;
	private String msgword;
	private String content;
	private String pic;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getDangercode() {
		return dangercode;
	}
	public void setDangercode(String dangercode) {
		this.dangercode = dangercode;
	}
	public String getDangercont() {
		return dangercont;
	}
	public void setDangercont(String dangercont) {
		this.dangercont = dangercont;
	}
	public String getDangertype() {
		return dangertype;
	}
	public void setDangertype(String dangertype) {
		this.dangertype = dangertype;
	}
	public String getGhs() {
		return ghs;
	}
	public void setGhs(String ghs) {
		this.ghs = ghs;
	}
	public String getDangerclass() {
		return dangerclass;
	}
	public void setDangerclass(String dangerclass) {
		this.dangerclass = dangerclass;
	}
	public String getMsgword() {
		return msgword;
	}
	public void setMsgword(String msgword) {
		this.msgword = msgword;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Danger [id=" + id + ", num=" + num + ", dangercode="
				+ dangercode + ", dangercont=" + dangercont + ", dangertype="
				+ dangertype + ", ghs=" + ghs + ", dangerclass=" + dangerclass
				+ ", msgword=" + msgword + ", content=" + content + ", pic="
				+ pic + "]";
	}
	
	public JSONObject toJsonObject() {
		JSONObject danger = new JSONObject();
		try {
			danger.accumulate("id", id);
			danger.accumulate("num", num);
			danger.accumulate("dangercode", dangercode);
			danger.accumulate("dangercont", dangercont);
			danger.accumulate("dangertype", dangertype);
			danger.accumulate("ghs", ghs);
			danger.accumulate("dangerclass", dangerclass);
			danger.accumulate("msgword", msgword);
			danger.accumulate("content", content);
			danger.accumulate("pic", pic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return danger;
	}
}
