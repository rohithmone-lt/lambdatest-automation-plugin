package com.lambdatest.jenkins.freestyle.report;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import hudson.model.Run;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lambdatest.jenkins.freestyle.api.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.logging.Logger;

public class AppiumReportBuildAction extends AbstractReportBuildAction {
    private final static Logger logger = Logger.getLogger(AppiumReportBuildAction.class.getName());
    private String lambdaTestBuildDeviceUrl;
    private String authString;
    private String buildName;
    List<JSONObject> result = new ArrayList<JSONObject>();

    public AppiumReportBuildAction(final Run<?, ?> build, String name,String password, String buildName) {
        super();
        setBuild(build);
        this.authString =  name + ":" + password;
        this.buildName = buildName;
    }
    public void generateLambdaTestAppiumReport() {
        logger.info("authString : " + authString);
        byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        logger.info("in generate generateLambdaTestAppiumReport function");
        try {
            URL buildUrl = new URL(Constant.AppiumReport.BUILD_INFO_URL);
            URLConnection buildUrlConnection = buildUrl.openConnection();
            buildUrlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            InputStream is = buildUrlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            int numCharsReadForBuild;
            char[] charArrayForBuild = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsReadForBuild = isr.read(charArrayForBuild)) > 0) {
                sb.append(charArrayForBuild, 0, numCharsReadForBuild);
            }
            String buildResult = sb.toString();
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(buildResult);
                ArrayNode dataNode = (ArrayNode) rootNode.get("data");
                Iterator<JsonNode> dataIterator = dataNode.elements();
                while (dataIterator.hasNext()) {
                    JsonNode buildDetailNode = dataIterator.next();
                    if (buildDetailNode.get("name").toString().replaceAll("\"", "").equals(buildName)) {
                        String build_id = buildDetailNode.get("build_id").toString();

                        lambdaTestBuildDeviceUrl = "https://appautomation.lambdatest.com/test?buildid=" + build_id;

                        logger.info("lambdaTestBuildBrowserUrl : " + lambdaTestBuildDeviceUrl);
                        URL sessionUrl = new URL(Constant.AppiumReport.SESSION_INFO_URL + "?limit=100&build_id=" + build_id);
                        URLConnection sessionUrlConnection = sessionUrl.openConnection();
                        sessionUrlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
                        is = sessionUrlConnection.getInputStream();
                        isr = new InputStreamReader(is);
                        int numCharsReadForSession;
                        char[] charArrayForSession = new char[1024];
                        sb = new StringBuffer();
                        while ((numCharsReadForSession = isr.read(charArrayForSession)) > 0) {
                            sb.append(charArrayForSession, 0, numCharsReadForSession);
                        }
                        String sessionResult = sb.toString();

                        JsonNode sessionRootNode = mapper.readTree(sessionResult);
                        ArrayNode sessionDataNode = (ArrayNode) sessionRootNode.get("data");
                        Iterator<JsonNode> sessionDataIterator = sessionDataNode.elements();

                        JSONArray array = new JSONArray();
                        while (sessionDataIterator.hasNext()) {
                            JsonNode sessionDetailNode = sessionDataIterator.next();
                            String testUrl = "https://appautomation.lambdatest.com/test?testID=" + sessionDetailNode.get("testId").toString().replaceAll("\"", "") + "&buildid=" + build_id;
                            logger.info("testUrl : " + testUrl);
                            JSONObject resultJSON = new JSONObject();
                            resultJSON.put("url",testUrl);
                            resultJSON.put("status",sessionDetailNode.get("test_status").toString().replaceAll("\"", ""));
                            resultJSON.put("device",sessionDetailNode.get("device_name").toString().replaceAll("\"", ""));
                            resultJSON.put("deviceVersion",sessionDetailNode.get("os_version").toString().replaceAll("\"", ""));
                            resultJSON.put("OS",sessionDetailNode.get("device_os").toString().replaceAll("\"", ""));
                            resultJSON.put("name",sessionDetailNode.get("test_name").toString().replaceAll("\"", ""));
                            resultJSON.put("testId",sessionDetailNode.get("testId").toString().replaceAll("\"", ""));
                            array.put(resultJSON);
                        }
                        for (int i = 0; i < array.length(); i++) {
                            result.add(array.getJSONObject(i));
                        }
                        logger.info(result.toString());
                        break;
                    }
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<JSONObject> getResult() {
        return result;
    }

    public String getLambdaTestBuilddeviceUrl() {
        return lambdaTestBuildDeviceUrl;
    }

    public String getBuildName() {
        return buildName;
    }
}
