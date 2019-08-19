package com.example.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class TestPageService {
	
	@Autowired
	private TestPageDao tpd;
	
	@Autowired
	private SampleHtml samplehtml;
	
	// dataテーブルのカラム名
	final String symbol = "symbol";
	final String menu = "kubun_name";
	final String large_id = "large_id";
	final String large = "large_name";
	final String middle_id = "middle_id";
	final String middle = "middle_name";
	final String category = "category";
	
	public ModelAndView top(ModelAndView mav) {
		mav.setViewName("top");
		return mav;
	}
	
	public ModelAndView sample(ModelAndView mav) {
		mav.setViewName("sample");
		return mav;
	}
	
	public ModelAndView test(ModelAndView mav) throws IOException {
		
		mav.setViewName("test");
		
		// dataテーブルのデータを取得
		final List<Map<String, Object>> select = tpd.select();
		final List<Map<String, Object>> select_color = tpd.color();
		String color = "color";
		String color_sub = "color_sub";
		
		// for文内での比較用Mapを作成、前回のデータとして扱う。
		Map<String, Object> comparison = new HashMap<>();
		
		// kubun,large,middleのカウントを作成
		// menuのidとa hrefのリンク先、largeのidに使用する。hrefのリンクとlargeのidを同じにしておかないとjsが動かない。
		// categoryのdivタグのidの作成に使用する。
		int kubun_count = 0;
		int large_count = 0;
		int middle_count = 0;
		
		//  largeのdivタグ内のid,classを作成
		String str_guide_id = "";
		String str_guide_class = "";
		
		//  large,middle,categoryのhtml用のStringを作成、symbolが変わるタイミングでmainに追加する。
		String html_main = "";
		String html_large = "";
		String html_middle = "";
		String html_category = "";
		
		//　menu部分のhtmlを作成
		String html_menu = "";
		String menu_li_class = "faq"; // 1つ目のクラスはfaqで固定。
		
		// css用
		String css = "@charset \"utf-8\";\r\n";
		
		// selectで取得したレコードを1つずつ順番に処理する。
		for(Map<String, Object> map : select) {
			
			// symbolに何も入っていない場合
			if(Objects.equals(map.get(symbol),null)) {
				// 次のレコードに進む。
				
			// kubun_nameの値で比較して処理を行うが、1回目だけkubun_countが0の処理を使う。
			} else if(kubun_count == 0) {
				// 1回目のみここで処理をする、kubun_countが0の場合
				// kubun,large,middleのカウントを1つずつ増やす(categoryのdiv id:1-1-1)
				kubun_count++;
				large_count++;
				middle_count++;
				
				// cssを作成
				css = css + "/*1色目*/\r\n"
						+ "#guide-bottom-navi #guide-bottom-menu "
						+ "li.guide-bottom-menu-"
						+ menu_li_class
						+ " a {\r\n"
						+ "    background:"
						+ String.valueOf(select_color.get(kubun_count-1).get(color)) //1色目
						+ ";\r\n"
						+ "}\r\n"
						+ "\r\n"
						+ "#guide-bottom-navi #guide-bottom-menu "
						+ "li.guide-bottom-menu-"
						+ menu_li_class
						+ " a:hover,\r\n"
						+ "#guide-bottom-navi #guide-bottom-menu "
						+ "li.guide-bottom-menu-"
						+ menu_li_class
						+ ".selected a {\r\n"
						+ "    background: "
						+ String.valueOf(select_color.get(kubun_count-1).get(color_sub)) //2色目
						+ ";\r\n" 
						+ "}\r\n"
						+ "\r\n"
						+ "#guide-bottom-selector > div.guide-bottom-"
						+ menu_li_class
						+ " {\r\n"
						+ "    border-color: "
						+ String.valueOf(select_color.get(kubun_count-1).get(color_sub)) //2色目
						+ ";\r\n"
						+ "}\r\n"
						+ "\r\n"
						+ "#guide-bottom-selector > div.guide-bottom-"
						+ menu_li_class
						+ " > div.guide-bottom-large {\r\n"
						+ "    background: "
						+ String.valueOf(select_color.get(kubun_count-1).get(color_sub)) //2色目
						+ ";\r\n"
						+ "}\r\n";
				
				// menu部分のhtmlを作成
				html_menu = html_menu
						+ "      "
						+ "<ul id=\"guide-bottom-menu\">\r\n"
						+ "        "
						+ "<li class=\"guide-bottom-menu-"
						+ menu_li_class+" selected\" "
						+ "id=\"guide-bottom-menu"+kubun_count+"\">"
						+ "<a href=\"#guide-bottom-"+kubun_count+"\">"
						+ String.valueOf(map.get(menu)) //mapで取得したmenuを表示する。
						+ "</a></li>\r\n";
				
				menu_li_class = "use"; // 2つ目はuseで固定のため変更しておく。
				
				// large部分のhtmlを作成
				html_large = html_large
						+ "        "
						+ "<div id=\"guide-bottom-"
						+ String.valueOf(kubun_count) // menuのhrefと、ここのidを一致させる。
						+ "\" class=\"guide-bottom-faq selected\">\r\n" // faqで固定
						+ "          <div class=\"guide-bottom-large\">\r\n" 
						+ "            <ul>\r\n"
						+ "            <li class=\"current-hover\">"
						+ "<a href=\"./list-middle-faq.php\">"
						+ String.valueOf(map.get(large)) //mapで取得したlargeを表示する。
						+ "</a></li>\r\n";
				
				html_middle = html_middle //middleのhtmlを作成
						+ "        "
						+ "<div class=\"guide-bottom-middle\">\r\n"
						+ "            <ul class=\"selected\">\r\n"
						+ "              <li class=\"current-hover\">"
						+ "<a href=\"./list-small-faq.php\">"
						+ String.valueOf(map.get(middle)) //mapで取得したmiddleを表示する。
						+ "</a></li>\r\n";
				
				html_category = html_category //categoryのhtmlを作成
						+ "        "
						+ "<div class=\"guide-bottom-small\">\r\n"
						+ "            "
						+ "<ul id=\"guide-bottom-small"
						+ kubun_count+"-"+large_count+"-"+middle_count //idをそれぞれのcountで作成。
						+ "\" class=\"selected\">\r\n"
						+ "              "
						+ "<li>"
						+ "<a href=\"./page-faq.php\">"
						+ String.valueOf(map.get(category)) //mapで取得したcategoryを表示する。
						+ "</a>"
						+ "</li>\r\n";
				
				//2回目以降、symbolの値を1つ前のレコードと比較する。
			}else if(Objects.equals(map.get(symbol),comparison.get(symbol))) {
				//symbolの値が前回と同じ場合、large_idの比較を行う。
				
				if(Objects.equals(map.get(large_id),comparison.get(large_id))) {
					//symbolが同じでlarge_idの値が同じ場合、middle_idの比較を行う。
					
					if(Objects.equals(map.get(middle_id),comparison.get(middle_id))) {
						//symbol、arge_id、middle_idの値が同じ場合
						//categoryを追加する。
						html_category = html_category
								+ "              "
								+ "<li><a href=\"./page-faq.php\">"
								+ String.valueOf(map.get(category))
								+ "</a></li>\r\n";
						
					}else {
						//symbol、large_idの値が同じで、middle_idの値が違う場合
						//middleを追加しmiddle_countを1つ増やす。categoryのタグを閉じて、新しく作成する。
						middle_count++;
						
						html_middle = html_middle
								+ "              <li>"
								+ "<a href=\"./list-middle-faq.php\">"
								+ String.valueOf(map.get(middle))
								+ "</a></li>\r\n";
						
						html_category = html_category
								+ "            </ul>\r\n"
								+ "            "
								+ "<ul id=\"guide-bottom-small"
								+ kubun_count+"-"+large_count+"-"+middle_count
								+ "\">\r\n"
								+ "              "
								+ "<li><a href=\"./page-faq.php\">"
								+ String.valueOf(map.get(category))
								+ "</a></li>\r\n";
					}
					
				}else {
					//symbolが同じでlarge_idの値が違う場合
					//largeを追加しlarge_countを1つ増やす。middleとcategoryのタグを閉じて、新しく作成する。
					//middleのカウントを1に戻す。
					large_count++;
					middle_count = 1;
					
					html_large = html_large
							+ "            <li><a href=\"./list-middle-faq.php\">"
							+ String.valueOf(map.get(large))
							+ "</a></li>\r\n";
					
					html_middle = html_middle
							+ "            </ul>\r\n"
							+ "\r\n"
							+ "            <ul>\r\n"
							+ "              <li>"
							+ "<a href=\"./list-middle-faq.php\">"
							+ String.valueOf(map.get(middle))
							+ "</a></li>\r\n";
					
					html_category = html_category
							+ "            </ul>\r\n"
							+ "            "
							+ "<ul id=\"guide-bottom-small"
							+ kubun_count+"-"+large_count+"-"+middle_count
							+ "\">\r\n"
							+ "              "
							+ "<li><a href=\"./page-faq.php\">"
							+ String.valueOf(map.get(category))
							+ "</a></li>\r\n";
				}
				
			}else {
				// symbolの値が前回と番う場合
				// large、middle、categoryのタグを閉じてmainに追加---A
				// kubun_countを増やし、largeとmiddleのcountを1に戻す--B
				// menuを追加、large、middle、categoryのタグを新たに作成する---C
				
				//---A
				html_large = html_large
						+ "            </ul>\r\n" 
						+ "          </div>\r\n";
				
				html_middle = html_middle
						+ "            </ul>\r\n" 
						+ "          </div>\r\n";
				
				html_category = html_category
						+ "            </ul>\r\n" 
						+ "          </div>\r\n";
				
				html_main = html_main + html_large + html_middle + html_category
						+ "          </div>\r\n"
						+ "\r\n";
				
				//---B
				kubun_count++;
				large_count = 1;
				middle_count = 1;
				
				if(kubun_count==2) {
					str_guide_id = String.valueOf(kubun_count);// kubun_menuのhrefと、idを一致させる。
					str_guide_class = "use"; // 2回目はuseで固定
				}else if(kubun_count>2){
					str_guide_id = String.valueOf(kubun_count);// kubun_menuのhrefと、idを一致させる。
					str_guide_class = "products"; // 3回目はproducsで固定
				}
				
				css = css + "/*"+kubun_count+"色目*/\r\n"
						+ "#guide-bottom-navi #guide-bottom-menu "
						+ "li.guide-bottom-menu-"
						+ menu_li_class
						+ " a {\r\n"
						+ "    background:"
						+ String.valueOf(select_color.get(kubun_count-1).get(color)) //1色目
						+ ";\r\n"
						+ "}\r\n"
						+ "\r\n"
						+ "#guide-bottom-navi #guide-bottom-menu "
						+ "li.guide-bottom-menu-"
						+ menu_li_class
						+ " a:hover,\r\n"
						+ "#guide-bottom-navi #guide-bottom-menu "
						+ "li.guide-bottom-menu-"
						+ menu_li_class
						+ ".selected a {\r\n"
						+ "    background: "
						+ String.valueOf(select_color.get(kubun_count-1).get(color_sub)) //2色目
						+ ";\r\n" 
						+ "}\r\n"
						+ "\r\n"
						+ "#guide-bottom-selector > div.guide-bottom-"
						+ menu_li_class
						+ " {\r\n"
						+ "    border-color: "
						+ String.valueOf(select_color.get(kubun_count-1).get(color_sub)) //2色目
						+ ";\r\n"
						+ "}\r\n"
						+ "\r\n"
						+ "#guide-bottom-selector > div.guide-bottom-"
						+ menu_li_class
						+ " > div.guide-bottom-large {\r\n"
						+ "    background: "
						+ String.valueOf(select_color.get(kubun_count-1).get(color_sub)) //2色目
						+ ";\r\n"
						+ "}\r\n"
						+ "\r\n";
				
				//---C
				html_menu = html_menu
						+ "        "
						+ "<li class=\"guide-bottom-menu-"+menu_li_class+"\" "
						+ "id=\"guide-bottom-menu"+kubun_count+"\">"
						+ "<a href=\"#guide-bottom-"+kubun_count+"\">"
						+ String.valueOf(map.get(menu))
						+ "</a></li>\r\n";
				
				menu_li_class = "products"; // 3回目以降はproductsで固定
				
				html_large = "        "
						+ "<div id=\""
						+ "guide-bottom-"
						+ str_guide_id //kubun_menuのhrefと、idを一致させる。
						+ "\" class=\"guide-bottom-"
						+ str_guide_class
						+ "\">\r\n"
						+ "\r\n"
						+ "          "
						+ "<div class=\"guide-bottom-large\">\r\n"
						+ "            <ul>\r\n"
						+ "              <li>"
						+ "<a href=\"use.php#guide-use-middle\">"
						+ String.valueOf(map.get(large))
						+ "</a></li>\r\n"
						+ "\r\n";
				
				html_middle = "          "
						+ "<div class=\"guide-bottom-middle\">\r\n"
						+ "            <ul>\r\n"
						+ "              <li>"
						+ "<a href=\"./page-use.php\">"
						+ String.valueOf(map.get(middle))
						+ "</a></li>\r\n";
				
				html_category = "          "
						+ "<div class=\"guide-bottom-small\">\r\n"
						+ "            "
						+ "<ul id=\"guide-bottom-small"
						+ kubun_count+"-"+large_count+"-"+middle_count
						+ "\">\r\n"
						+ "              <li>"
						+ "<a href=\"./page-use.php\">"
						+ String.valueOf(map.get(category))
						+ "</a></li>\r\n";
				
			}
			
			comparison = map; //comparisonに今回のレコードを代入、次のレコードとの比較用として使う。
		
		} // for拡張文はここまで
		
		// large,middle,categoryのhtmlに閉じるタグをつける。
		html_menu = html_menu
				+ "      </ul>\r\n";
				
				
		html_large = html_large 
				+ "            </ul>\r\n" 
				+ "          </div>\r\n";
		
		html_middle = html_middle
				+ "            </ul>\r\n" 
				+ "          </div>\r\n";
		
		html_category = html_category
				+ "            </ul>\r\n" 
				+ "          </div>\r\n";
		
		// mainに追加
		html_main = html_main + html_large + html_middle + html_category;
		
		// test.htmlに送るhtml
		String html_testpage = "<div id=\"guide-bottom-navi\">\r\n"
				+ html_menu +"\r\n"
				+ "<div id=\"guide-bottom-selector\">\r\n"
				+ html_main
				+ "</div>"
				+ "</div>";
		
		// "${testpage}"にtestpageを追加する。
		mav.addObject("testpage", html_testpage);
		
		// sample、課題の比較用
		mav.addObject("pagebody", samplehtml.samplebody());
		
		// color.cssのfileを作成
		String static_css = "C:\\Users\\tokuda\\SpringToolSuite"
				+ "\\test0805\\src\\main\\resources"
				+ "\\static\\css\\newcolor.css";
		File file = new File(static_css);
		FileWriter color_css = new FileWriter(static_css);
        PrintWriter pw = new PrintWriter(new BufferedWriter(color_css));
		if (file.createNewFile()){
            System.out.println("ファイル作成"); 
            pw.println(css);
            pw.close();
        }else{
            System.out.println("ファイル上書き");
            pw.println(css);
            pw.close();
        }
		
		return mav;
	}

}
