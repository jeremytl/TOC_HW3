/**
 * @author ���n�m Wei-Jun Chen
 * student number : F74006129
 * description:
 * 		Parse the .json data from the URL(args[0]),
 * then find the average of real price specified by argument list
 *
 */
import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class TocHW3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		if (args.length < 4) {
			System.out.println("Too few arguments !");
			return;
		} else if (Integer.parseInt(args[3]) >= 104) {
			System.out
					.println("You can't find the trading data in the future !");
			return;
		}

		BufferedReader readjson = new BufferedReader(new InputStreamReader(
				new URL(args[0]).openStream(), "UTF-8"));
		String jsontext = new String("");
		Scanner scan = new Scanner(readjson);
		while (scan.hasNext())
			jsontext += scan.nextLine();

		// jsontext+="]";

		scan.close();
		readjson.close();

		// System.out.println(jsontext);

		JSONArray realpricejson = new JSONArray(jsontext);

		Pattern sectionp = Pattern.compile(args[1]);
		Pattern locationp = Pattern.compile(args[2]);

		int i, avg_price = 0, cnt = 0;
		int earliesty = Integer.parseInt(args[3]) * 100;
		int matchery;

		JSONObject aline = realpricejson.getJSONObject(0);
		for (i = 0; i < realpricejson.length(); i++) {
			aline = realpricejson.getJSONObject(i);
			matchery = aline.getInt("����~��");
			if (sectionp.matcher(aline.getString("�m����")).find()
					&& locationp.matcher(aline.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P"))
							.find() && matchery > earliesty) {
				avg_price += aline.getInt("�`����");
				cnt++;
			}
		}

		if (cnt != 0) {
			avg_price /= cnt;
			System.out.println(avg_price);
		} else {
			System.out.println("No data matches " + args[1] + args[2]
					+ " and trade at/atfer " + args[3]);
		}

	}
}
