package com.cyou.mrd.disunityweb.data;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cyou.mrd.disunityweb.core.domain.application.ApplicationFactory;
import com.cyou.mrd.disunityweb.core.domain.application.UnityApplication;

public class DatabaseAnalyzeResult {
	private Statement statement = null;
	private UUID fileId;
	private String type = null;
	private long totalSize = 0;

	public DatabaseAnalyzeResult(UUID fileId) {
		statement = Database.Instane().begin();
		this.type = null;
		this.fileId = fileId;
	}

	public DatabaseAnalyzeResult(UUID fileId, String type) {
		statement = Database.Instane().begin();
		this.type = type;
		this.fileId = fileId;
	}

	private String getTopSql(String cname) {
		String sql = "select cid as classid,cname as classname,size,pname as name,source as sourcefile from assets where uuid='"
				+ fileId.toString() + "'";
		if (type != null) {
			sql += " and type='" + type + "'";
		}

		if (cname != null) {
			sql += " and cname='" + cname + "'";
		}
		sql += " ORDER BY size desc limit 0,60;";
		return sql;
	}
	
	private String getRepeatSql() {
//		String sql = "select assets.pname as name,source as sourcefile,assets.size from assets,"
//				+ "( select pname,size from assets "
//					+ "where uuid='"+ fileId.toString() +"' and pname not like '(unname)' group by pname,size having count(*) > 1 ) t2"
//				+ " where assets.pname = t2.pname and assets.size = t2.size "
//				+ "and uuid='" + fileId.toString() + "'"
//				+ " order by assets.size desc, name desc;";
		
		//select pname as name,source as sourcefile,size,assets.md5 from assets,( select md5 from assets where uuid='b64c65bc-fc8d-4850-85e3-cedf63d9c8bb'group by md5 having count(*) > 1 ) t2 where assets.md5=t2.md5 order by assets.md5
		String sql = "select pname as name,cname as classname,source as sourcefile,size,assets.md5 from assets,"
					+ "( select md5 from assets where "
					+ "uuid='"+fileId.toString()+"'group by md5 having count(*) > 1 ) t2"
				+ " where assets.md5=t2.md5 and uuid='"+fileId.toString()+"'"
				+ " order by assets.md5;";
		
		return sql;
	}

	private String getTotalSizeSql() {
		String sql = "select sum(size) from assets where uuid='"
				+ fileId.toString() + "'";
		if (type != null) {
			sql += " and type='" + type + "'";
		}
		sql += ";";
		return sql;
	}

	private String getPrecentSql() {
		String sql = "SELECT cid as classid, cname as classname, 100*sum(size)/";
		sql += totalSize;
		sql += " as percent, sum(size)/1048576 as size from assets where uuid='" + fileId.toString() + "'";

		if (type != null) {
			sql += " and type='" + type + "'";
		}
		sql += " GROUP BY cname ORDER BY percent desc;";
		return sql;
	}

	private JSONArray getTopReuslt(String cname) {
		JSONArray array = new JSONArray();
		try {
			ResultSet rs = statement.executeQuery(getTopSql(cname));
			ResultSetMetaData rsmd = rs.getMetaData();
			int length = rsmd.getColumnCount();
			while (rs.next()) {
				JSONObject data = new JSONObject();
				for (int index = 1; index <= length; ++index) {
					data.put(rsmd.getColumnName(index), rs.getString(index));
				}
				array.put(data);
			}
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		} finally {

		}
		return array;
	}
	
	private JSONArray getRepeatReuslt() {
		JSONArray array = new JSONArray();
		try {
			ResultSet rs = statement.executeQuery(getRepeatSql());
			ResultSetMetaData rsmd = rs.getMetaData();
			int length = rsmd.getColumnCount();
			
			/**
			 * 返回的Json格式
			 * [
			 * 	{"name":"res1","classname":"","size":"","quote":[{"name":"quote1", "size":""},{"name":"quote2"},{"name":"quote3"}]},
			 * 	{"name":"res2","quote":[{"name":"quote1"},{"name":"quote2"},{"name":"quote3"}]},
			 * 	{"name":"res3","quote":[{"name":"quote1"},{"name":"quote2"},{"name":"quote3"}]}
			 * ]
			 */
			JSONObject currentJson = null;
			JSONArray currentQuote = null;
			byte[] currentMD5 = null;
//			while (rs.next()) {
////				if (currentJson == null || !currentJson.get("name").equals(rs.getString("name"))) {
//				if (currentJson == null || currentMD5 == null || !Arrays.equals(currentMD5, rs.getBytes("md5"))) {
//					//创建新的JSONObject
//					currentJson = new JSONObject();
//					currentQuote = new JSONArray();
//					currentJson.put("name", rs.getString("name"));
//					currentJson.put("quote", currentQuote);
//					currentMD5 = rs.getBytes("md5");
//					System.out.println("---- new file ---- " + currentJson.getString("name")+" md5 : "+rs.getBytes("md5"));
//					//当前为新文件名时,将当前JSONObject存到结果数组中
//					array.put(currentJson);
//				}
//				//生成单个节点{"name":"quote1", "size":""}
//				JSONObject node = new JSONObject();
//				node.put("name", rs.getString("sourcefile"));
//				node.put("size", rs.getString("size"));
//				currentQuote.put(node);
////				JSONObject data = new JSONObject();
////				for (int index = 1; index <= length; ++index) {
////					data.put(rsmd.getColumnName(index), rs.getString(index));
////				}
////				array.put(data);
//			}
			
			
			// 拿map重写一遍吧。。。
			Map<String,RepeatFile> jsonMap = new HashMap<String, DatabaseAnalyzeResult.RepeatFile>();
			while (rs.next()) {
				String md5Str = new String(rs.getBytes("md5"), "utf-8");
				if (jsonMap.containsKey(md5Str)) {
					jsonMap.get(md5Str).add(rs.getString("sourcefile"));
				} else {
					jsonMap.put(md5Str, new RepeatFile(rs.getString("name"), rs.getString("classname"), Double.valueOf(rs.getString("size")).longValue(), rs.getString("sourcefile")));
				}
				
				
				
//				if (currentMD5 == null || !Arrays.equals(currentMD5, rs.getBytes("md5"))) {
//					currentMD5 = rs.getBytes("md5");
//					System.out.println("---- new file ---- " + currentJson.getString("name")+" md5 : "+rs.getBytes("md5"));
//					curFile = new RepeatFile(rs.getString("name"), rs.getString("classname"), Long.valueOf(rs.getString("size")), rs.getString("sourcefile"));
//					jsonMap.put(new String(rs.getString("md5")), curFile);
//				}
			}
			
			// 遍历jsonmap生成json
			ArrayList<RepeatFile> dataArr = new ArrayList<DatabaseAnalyzeResult.RepeatFile>(jsonMap.values());
			Collections.sort(dataArr);
			
			for (RepeatFile tmp : dataArr) {
				array.put(tmp.getJSONObject());
			}
			
			
		} catch (SQLException | UnsupportedEncodingException e) {
			System.out.print(e.getMessage());
		} finally {

		}
		return array;
	}
	
	private class RepeatFile implements Comparable<RepeatFile> {
		//{"name":"res1","classname":"","size":"","quote":[{"name":"quote1", "size":""},{"name":"quote2"},{"name":"quote3"}]},
		private String name;
		private String classname;
		private long singleSize;
		private long size;
		public ArrayList<Quote> quote = new ArrayList<Quote>();
		
		public RepeatFile(String name, String classname, long size, String souceFileName) {
			this.name = name;
			this.classname = classname;
			this.singleSize = size;
			quote.add(new Quote(souceFileName,this.singleSize));
		}
		
		public void add(String souceFileName) {
			quote.add(new Quote(souceFileName,this.singleSize));
			calSize();
		}
		
		public void calSize() {
			this.size = this.singleSize * quote.size();
		}
		
		class Quote {
			private String name;
			private long size;
			
			public Quote(String name, long size) {
				this.name = name;
				this.size = size;
			}

			public String getName() {
				return name;
			}

			public long getSize() {
				return size;
			}

		}

		@Override
		public int compareTo(RepeatFile o) {
//			if (this.size > o.size) {
//				return 1;
//			} else {
//				return 0;			
//			}
			return this.size < o.size ? 1:-1;
		}
		
		public JSONObject getJSONObject() {
			JSONObject node = new JSONObject();
			JSONArray quoteArr = new JSONArray();
			
			node.put("name", this.name);
			node.put("classname", this.classname);
			node.put("size", this.size);
			node.put("quote", quoteArr);
			
			for (Quote tmp : quote) {
				JSONObject sourceFile = new JSONObject();
				sourceFile.put("name", tmp.name);
				sourceFile.put("size", String.valueOf(tmp.size));
				quoteArr.put(sourceFile);
			}
			
			return node;
		}
	}

	private boolean updateTotalSize() {
		try {
			ResultSet rs = statement.executeQuery(getTotalSizeSql());
			if (rs.next()) {
				totalSize = rs.getLong(1);
				return true;
			}
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
		return false;
	}

	private JSONArray getPrecent() {
		JSONArray array = new JSONArray();
		try {
			ResultSet rs = statement.executeQuery(getPrecentSql());
			while (rs.next()) {
				if (rs.getLong(3) < 1) {
					continue;
				}
				JSONObject data = new JSONObject();
				data.put("classid", rs.getString(1));
				data.put("classname", rs.getString(2));
				data.put("percent", rs.getLong(3));
				data.put("size", rs.getLong(4));
				array.put(data);
			}
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
		return array;
	}
	
	public JSONObject getResult() {
		JSONObject object = null;
		updateTotalSize();
		UnityApplication ua = ApplicationFactory.data.get(fileId);
		if(ua != null && ua.getState() != 1)
		{
			String state = ua.getState() + "";
			object = new JSONObject();
			object.put("state", state);
			return object;
		}
		if(totalSize == 0)
		{
			object = new JSONObject();
			object.put("state", "-2");
			return object;
		}
		try {
			ResultSet rs = statement.executeQuery("SELECT result from analresult where uuid = '"+fileId.toString()+"';");
			object = new JSONObject(rs.getString("result"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		object.put("percentage", getPrecent());//x 根据请求type进行查询
		
		return object;
	}

	public JSONObject getAnalResult() {
		JSONObject object = new JSONObject();
		
		updateTotalSize();
		
		UnityApplication ua = ApplicationFactory.data.get(fileId);
		if(ua != null && ua.getState() != 1)
		{
			String state = ua.getState() + "";
			object.put("state", state);
			return object;
		}
		if(totalSize == 0)
		{
			object.put("state", "-2");
			return object;
		}

		object.put("state", "1");
		object.put("repeatRes", getRepeatReuslt());
		object.put("top30", getTopReuslt(null));
		object.put("Texture2D", getTopReuslt("Texture2D"));
		object.put("AnimationClip", getTopReuslt("AnimationClip"));
		object.put("AudioClip", getTopReuslt("AudioClip"));
		object.put("Mesh", getTopReuslt("Mesh"));
		object.put("CombineMesh", getTopReuslt("Combined Mesh"));
		object.put("LightProb", getTopReuslt("LightProbeCloud"));
//		object.put("percentage", getPrecent());//x 将百分比放在每次请求的时候进行查询
		object.put("size", totalSize / (1024 * 1024)); //M
		return object;
	}
}
