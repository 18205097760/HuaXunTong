package Api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nju.model.ChemBasic;
import com.nju.model.ChemName;
import com.nju.model.Danger;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLiteApi {
	static String DB_PATH = "/data/data/com.nju.chemtrack/databases/"; 
	static String DB_NAME = "chemtrack.db"; 
	static SQLiteDatabase database = null;
    private static void openDatabase(Activity activity) {
    	File sqlfile = new File(DB_PATH + DB_NAME);
    	   // 检查 SQLite 数据库文件是否存在  
        if (sqlfile.exists() == false) { 
            // 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在  
            File f = new File(DB_PATH); 
            // 如 database 目录不存在，新建该目录  
            if (!f.exists()) { 
                f.mkdir(); 
            } 
 
 
            try { 
                // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流  
                InputStream is = activity.getBaseContext().getAssets().open(DB_NAME); 
                // 输出流  
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME); 
                // 文件写入  
                byte[] buffer = new byte[1024]; 
                int length; 
                while ((length = is.read(buffer)) > 0) { 
                    os.write(buffer, 0, length); 
                } 
 
 
                // 关闭文件流  
                os.flush(); 
                os.close(); 
                is.close(); 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
        }
        database = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null); 
    }
    
    public static JSONObject getJsonObject(String url,HashMap<String,String> map,Activity activity){
    	if(database == null){
    		openDatabase(activity);
    	}
        JSONObject jsonObject = new JSONObject();
        if(url.equals("GetBasic")){
        	String scanResult = map.get("identification");
        	String cas = map.get("cas");
        	String name = map.get("name");
        	String sqlString = "Select cb.* from chembasic cb, chemname cn where 1=1 and cn.CAS = cb.CAS" ;
        	if(cas!=null && !cas.equals("")){
    			sqlString +=" and cn.CAS ='"+cas+"' ";
    		}
    		if(name!=null && !name.equals("")){
    			sqlString +=" and cn.NAME ='"+name+"' ";
    		}
    		if(scanResult!=null && !scanResult.equals("")){
    			sqlString +=" and cn.IDENTIFICATION ='"+scanResult+"' ";
    		} 
    		Cursor c = database.rawQuery(sqlString, null);  
    		ChemBasic chemBasic = new ChemBasic();
    		while (c.moveToNext()) {   
    			chemBasic.setId(c.getInt(c.getColumnIndex("cb.ID")));
				chemBasic.setCas(c.getString(c.getColumnIndex("cb.CAS")));
				chemBasic.setName(c.getString(c.getColumnIndex("cb.NAME")));
				chemBasic.setOthername(c.getString(c.getColumnIndex("cb.OTHERNAME")));
				chemBasic.setMolfor(c.getString(c.getColumnIndex("cb.MOLFOR")));
				chemBasic.setMolwei(c.getString(c.getColumnIndex("cb.MOLWEI")));
				chemBasic.setPhysical(c.getString(c.getColumnIndex("cb.PHYSICAL")));
				chemBasic.setHealth(c.getString(c.getColumnIndex("cb.HEALTH")));
				chemBasic.setEnvironment(c.getString(c.getColumnIndex("cb.ENVIRONMENT")));
				chemBasic.setMsgword(c.getString(c.getColumnIndex("cb.MSGWORD")));
				chemBasic.setRef(c.getString(c.getColumnIndex("cb.REF")));
    			break;
    		}  
            c.close();
            chemBasic = getDanger(chemBasic);
            try {
				jsonObject.accumulate("key", 1);
				jsonObject.accumulate("chemBasic", chemBasic.toJsonObject());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}
            return jsonObject;
        }
        
        if(url.equals("GetList")){
        	String scanResult = map.get("identification");
        	String cas = map.get("cas");
        	String name = map.get("name");
        	String sqlString = "Select * from chemname where 1=1 " ;
        	if(cas!=null && !cas.equals("")){
    			sqlString +=" and CAS like '%"+cas+"%' ";
    		}
    		if(name!=null && !name.equals("")){
    			sqlString +=" and NAME like '%"+name+"%' ";
    		}
    		if(scanResult!=null && !scanResult.equals("")){
    			sqlString +=" and IDENTIFICATION like '%"+scanResult+"%' ";
    		} 
    		Cursor c = database.rawQuery(sqlString, null);  
    		ArrayList<ChemName> result = new ArrayList<ChemName>();
			while (c.moveToNext()) { 
				ChemName tmp = new ChemName();
				tmp.setCas(c.getString(c.getColumnIndex("CAS")));
				tmp.setName(c.getString(c.getColumnIndex("NAME")));
				tmp.setIdentification(c.getString(c.getColumnIndex("IDENTIFICATION"))); 
				result.add(tmp);
            }
			JSONArray jsonArray = new JSONArray();
			for(int i = 0;i<result.size();i++){
				jsonArray.put(result.get(i).toJsonObject());
			}
			try {
				jsonObject.accumulate("key", 1);
				jsonObject.accumulate("List", jsonArray);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObject;
        }
		return null;	  
    }

    public static ChemBasic getDanger(ChemBasic chemBasic){
		String phy = chemBasic.getPhysical();
		String hea = chemBasic.getHealth();
		String env = chemBasic.getEnvironment();
		int phynum,heanum,envnum;
		if (phy ==  null || phy.equals("")){	
			phy = ""; 
			phynum = 0;
		}
		else{
			phynum= phy.split(";").length;
		}
		
		if (hea ==  null ||  hea.equals("")){	
			hea = ""; 
			heanum = 0;
		}
		else{
			heanum= hea.split(";").length;
		}
		
		if (env ==  null || env.equals("")){	
			env = ""; 
			envnum = 0;
		}
		else{
			envnum= env.split(";").length;
		}
		
		String sql = "Select cd.*, cp.PIC from chemdanger cd,chempic cp where (cd.NUM in ('"+ phy.replaceAll(";", "','") 
				+ "') or cd.NUM in ('"+ hea.replaceAll(";", "','") 
				+ "') or cd.NUM in ('" + env.replaceAll(";", "','") 
				+ "') )and "+ " cd.NUM = cp.NUM order by cd.NUM"; 
		
		try {
			Log.d("sql", sql);
			Cursor c = database.rawQuery(sql,null);
			ArrayList<Danger> dangers = new ArrayList<Danger>();
			String pics = "";
			while (c.moveToNext()) { 
				Danger danger = new Danger();
				danger.setId(c.getInt(c.getColumnIndex("cd.ID")));
				danger.setNum(c.getString(c.getColumnIndex("cd.NUM")));
				danger.setDangerclass(c.getString(c.getColumnIndex("cd.DANGERCLASS")));
				danger.setDangercode(c.getString(c.getColumnIndex("cd.DANGERCODE")));
				danger.setDangercont(c.getString(c.getColumnIndex("cd.DANGERCONT")));
				danger.setDangertype(c.getString(c.getColumnIndex("cd.DANGERTYPE")));
				danger.setContent(c.getString(c.getColumnIndex("cd.CONTENT")));
				danger.setGhs(c.getString(c.getColumnIndex("cd.GHS")));
				danger.setPic(c.getString(c.getColumnIndex("cp.PIC")));
				danger.setMsgword(c.getString(c.getColumnIndex("cd.MSGWORD")));
				if(!pics.contains(danger.getPic())){
					pics = pics + danger.getPic() + ";";
				}
				dangers.add(danger);
				Log.d("NUM",danger.getNum());
            }
			JSONArray jsonArray = new JSONArray();
			for(int i=0;i<phynum;i++){
				jsonArray.put(dangers.get(i).toJsonObject());
			}
			chemBasic.setPhysicals(jsonArray);
			
			jsonArray = new JSONArray();
			for(int i=0;i<heanum;i++){
				jsonArray.put(dangers.get(phynum + i).toJsonObject());
			}
			chemBasic.setHealths(jsonArray);
			
			jsonArray = new JSONArray();
			for(int i=0;i<envnum;i++){
				jsonArray.put(dangers.get(phynum + heanum + i).toJsonObject());
			}
			chemBasic.setEnvironments(jsonArray);
			
			chemBasic.setPics(pics.substring(0,pics.length()-1));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return chemBasic;
	}

}
