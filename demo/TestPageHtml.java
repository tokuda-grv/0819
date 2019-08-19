package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

//別の方法でmenuのhtmlを作成するようになったので不要。
@Service
public class TestPageHtml {
	
	public String menu(List<String> symbol,List<String> kubun_name) {
		System.out.println(symbol);
		System.out.println(kubun_name);
		int count = 0; //symbolの数だけ回すためのカウントをとる
		int size = symbol.size();
		String li = "";
		String li_class = null;
		String li_id = null;
		String li_a = null;
		String a = null;
		String ul = "<ul id=\"guide-bottom-menu\">";
		while(count < size) {
			try {
			switch (count) {
			case 0:
				li_class = "faq selected";
				li_id = String.valueOf(count+1);
				li_a = String.valueOf(count+1);//"faq";
				a = kubun_name.get(count);
				break;
			case 1:
				li_class = "use";
				li_id = String.valueOf(count+1);
				li_a = String.valueOf(count+1);//"use";
				a = kubun_name.get(count);
				break;
			case 2:
				li_class = "products";
				li_id = String.valueOf(count+1);
				li_a = String.valueOf(count+1);//"instuments";
				a = kubun_name.get(count);
				break;
			default:
				li_class = "products";
				li_id = String.valueOf(count+1);
				li_a = String.valueOf(count+1);
				a = kubun_name.get(count);
				break;
			}
			li = li + "<li class=\"guide-bottom-menu-"+li_class+"\" "
					+ "id=\"guide-bottom-menu"+li_id+"\">"
					+ "<a href=\"#guide-bottom-"+li_a+"\">"
					+a+"</a></li>\r\n";
			//count++;
			}catch (IndexOutOfBoundsException e){
				System.out.println("エラー発生");
				System.out.println(e);
				
				li_class = "products";
				li_id = String.valueOf(count+1);
				li_a = String.valueOf(count+1);
				
				a = "メニュー"+String.valueOf(count+1);
				
				li = li + "<li class=\"guide-bottom-menu-"+li_class+"\" "
						+ "id=\"guide-bottom-menu"+li_id+"\">"
						+ "<a href=\"#guide-bottom-"+li_a+"\">"
						+a+"</a></li>\r\n";
				
			}finally {
				count++;
			}
			//count++;
		}
		
		return ul + li + "</ul>";
	}

}
