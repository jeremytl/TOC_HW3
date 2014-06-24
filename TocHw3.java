/**
 * @author 陳緯峻 Wei-Jun Chen
 * student number : F74006129
 * description:
 * 		Parse the .json data from the URL(args[0]),
 * then find the average of real price specified by argument list
 *
 */
import java.io.*;
import java.net.URL;

//import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class TocHw3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, JSONException {

		try {

			BufferedReader readjson = new BufferedReader(new InputStreamReader(
					new URL(args[0]).openStream(), "UTF-8"));
			StringBuilder jsontext = new StringBuilder();
			String curline;
			while ((curline = readjson.readLine()) != null)
				jsontext.append(curline);

			// jsontext+="]";

			readjson.close();
			// System.out.println("WTF why I parse so much long time using String's "+=" without StringBuilder.append!!!");
			// System.out.println(jsontext);

			JSONArray realpricejson = new JSONArray(jsontext.toString());

			Pattern sectionp = Pattern.compile(args[1]);
			Pattern locationp = Pattern.compile(args[2]);

			int i, avg_price = 0, cnt = 0;
			int earliesty = Integer.parseInt(args[3]) * 100;

			for (i = 0; i < realpricejson.length(); i++) {
				if (sectionp.matcher(
						realpricejson.getJSONObject(i).getString("鄉鎮市區"))
						.find()
						&& locationp.matcher(
								realpricejson.getJSONObject(i).getString(
										"土地區段位置或建物區門牌")).find()
						&& realpricejson.getJSONObject(i).getInt("交易年月") > earliesty) {
					avg_price += realpricejson.getJSONObject(i).getInt("總價元");
					cnt++;
				}
			}

			if (cnt != 0) {
				avg_price /= cnt;
				System.out.println(avg_price);
			} else {
				System.out.println("No data matches " + args[1] + args[2]
						+ " and trade at/after " + args[3]);
			}
		} catch (IOException e) {
			
				System.out.println("Too few arguments or error argument format!");
				e.printStackTrace();
				
		}

	}
}
