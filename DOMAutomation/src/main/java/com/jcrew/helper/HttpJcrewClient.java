package com.jcrew.helper;

import java.io.*;
import java.net.URL;

import com.cucumber.listener.Reporter;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
@SuppressWarnings("unused")
public class HttpJcrewClient {
    private static Header[] headers;
    private CloseableHttpClient httpClient;
    private static CloseableHttpResponse response;
    private int statusCode;
    private String statusMessage;

    public void executeRequest(String uri, String verb, String requestData) throws IOException {
        if (verb.equalsIgnoreCase("GET")) {
            doGetOnPath(uri, "");
        } else if (verb.equalsIgnoreCase("POST")) {
            doPostOnPath(uri, requestData);
        } else if (verb.equalsIgnoreCase("PUT")) {
            doPutOnPath(uri, requestData);
        } else if (verb.equalsIgnoreCase("DELETE")) {
            doDeleteOnPath(uri, "");
        } else {
            throw new UnsupportedOperationException(verb);
        }
    }

    
	public void doGetOnPath(String uri, String authToken) throws IOException {
        try {
            httpClient = HttpClients.createDefault();
            String inputUrl = uri;
            HttpGet httpGet = new HttpGet(inputUrl);
            httpGet.addHeader("Authorization", authToken);
            response = httpClient.execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            statusMessage = response.getStatusLine().getReasonPhrase();
            headers = response.getAllHeaders();
            String jsonData = getJsonDataFromInputStream(response.getEntity().getContent());
        } finally {
            //response.close();
        }
    }

    public void doDeleteOnPath(String uri, String authToken) throws IOException {
        try {
            httpClient = HttpClients.createDefault();
            String inputUrl = uri;
            HttpDelete httpDelete = new HttpDelete(inputUrl);
            httpDelete.addHeader("Authorization", authToken);
            response = httpClient.execute(httpDelete);
            statusCode = response.getStatusLine().getStatusCode();
            statusMessage = response.getStatusLine().getReasonPhrase();
            headers = response.getAllHeaders();
            String jsonData = getJsonDataFromInputStream(response.getEntity().getContent());
        } finally {
            response.close();
        }
    }

    public void doPostOnPath(String uri, String body) throws IOException {
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(uri);
            StringEntity input = new StringEntity(body);

            input.setContentType("application/json");
            httpPost.setEntity(input);
            httpPost.setHeader("Authorization", "");
            response = httpClient.execute(httpPost);
            statusCode = response.getStatusLine().getStatusCode();
            statusMessage = response.getStatusLine().getReasonPhrase();
            headers = response.getAllHeaders();
            String jsonData = getJsonDataFromInputStream(response.getEntity().getContent());
        } finally {
            response.close();
        }
    }

    public void doPutOnPath(String uri, String body) throws IOException {
        httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(uri);
        StringEntity input = new StringEntity(body);

        try {
            input.setContentType("application/json");
            httpPut.setEntity(input);
            httpPut.setHeader("Authorization", "");
            response = httpClient.execute(httpPut);
            statusCode = response.getStatusLine().getStatusCode();
            statusMessage = response.getStatusLine().getReasonPhrase();
            headers = response.getAllHeaders();
            String jsonData = getJsonDataFromInputStream(response.getEntity().getContent());
        } finally {
            response.close();
        }
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    @SuppressWarnings("static-access")
	public Header[] getAllResponseHeaders() {
        return this.headers;
    }

    private String getJsonDataFromInputStream(InputStream inputStream) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line = "";
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void downloadFile(String srcUrl, String destLocFilePath) {
        if (srcUrl.contains("http")) {
            try {
                File file = new File(destLocFilePath);
                URL myUrl = new URL(srcUrl);
                FileUtils.copyURLToFile(myUrl, file);
            } catch (Exception e) {
                Reporter.addStepLog("Unable download file from url: " + srcUrl + " so skipping this file comparision");
            }
        }
    }

    public static boolean isLinkStatusIsOk(String url) {
        boolean isLinkStatusIsOk = false;
        try {
            HttpJcrewClient http = new HttpJcrewClient();
            http.doGetOnPath(url, "");
            System.out.println(http.statusCode);
            if (http.statusCode == 200) {
                isLinkStatusIsOk = true;
            }
            /*else if (http.statusCode == 404){
                isLinkStatusIsOk=true;
                Reporter.addStepLog("Hmm.. page is displayed for foww ");
            }*/
        } catch (Exception e) {
            Reporter.addStepLog("");
        }
        return isLinkStatusIsOk;
    }
}
