package download;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;



public class Main {
	public static void main(String[] args){
		String result=httpURLGETJson();
		JSONArray img = jsonGetImgArray(result);
		for(int i=0;i<img.size();i++) {
			try {
				imgDownload(img.getString(i));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Download Failed!");
			}
			System.out.println(img.get(i));
		}

	}
	private static void imgDownload(String imgUrl) throws IOException {
		URL url1 = new URL(imgUrl);
		String filename = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		URLConnection uc = url1.openConnection();
        InputStream inputStream = uc.getInputStream();
        FileOutputStream out = new FileOutputStream("D:\\Photo\\"+filename);
        int j = 0;
        while ((j = inputStream.read()) != -1) {
            out.write(j);
        }
        inputStream.close();
        out.close();
	}
	private static JSONArray jsonGetImgArray(String result) {
		JSONObject json=JSON.parseObject(result);
		json=(JSONObject) json.get("data");
		json=(JSONObject) json.get("post");
		json=(JSONObject) json.get("post");
		JSONArray img = (JSONArray) json.get("images");
		return img;
	}
	
	private static String httpURLGETJson() {
        String methodUrl = "";
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String line = null;
        String cookie = "UM_distinctid=17ec7d1c26e92a-07676ce300cbdb-f791539-144000-17ec7d1c26fbc0; _ga=GA1.2.1672449001.1644029921; _gid=GA1.2.1819934916.1644029921;  _MHYUUID=b6b92827-8f8c-40cb-9237-1a3cad7c922d; _gat=1;";
        String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36";
        String re = "https://bbs.mihoyo.com/";
        try {
            URL url = new URL(methodUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", cookie);
            connection.setRequestProperty("User-Agent", ua);
            connection.setRequestProperty("Referer", re);
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder result = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));
                }
                System.out.println(result.toString());
                return result.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return null;
    }

	
}
