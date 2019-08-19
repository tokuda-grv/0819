package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestPageDao {
	
	@Autowired
	private JdbcTemplate jdbc;
	
	public List<Map<String, Object>> select(){
		// dataテーブルから必要な項目を取得、symbol,large,middle,categoryの順で並び替える。
		String sql_order_by = "select "
				+ "symbol, kubun_name, "
				+ "large_id, large_name, "
				+ "middle_id, middle_name, "
				+ "category_id, category "
				+ "from data "
				+ "order by "
				+ "symbol asc, "
				+ "large_id asc, "
				+ "middle_id asc, "
				+ "category_id asc;";
		return jdbc.queryForList(sql_order_by);
	}
	
	public List<Map<String, Object>> color(){
		// colorテーブルからidとcolorを取得する。
		String sql = "select "
				+ "id, color, color_sub "
				+ "from color;";
		return jdbc.queryForList(sql);
	}

}
