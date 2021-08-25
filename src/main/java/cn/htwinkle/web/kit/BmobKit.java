package cn.htwinkle.web.kit;

import cn.htwinkle.web.bean.User;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.apache.log4j.Logger;

import java.util.List;

public enum BmobKit {

    INSTANCE;

    /**
     * BmobKit的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(BmobKit.class.getName());

    public static final String X_Bmob_Application_Id = "7ab542cd39cb59c8997ab480e081b206";

    public static final String X_Bmob_REST_API_Key = "77bae2e924533d49b2b47356ad7034dc";

    public static final String X_Bmob_Master_Key = "92c300429ca7e61d7737e9f3be819289";

    /**
     * GET
     */
    public static final String LOGIN_URL = "https://api2.bmob.cn/1/login";

    /**
     * GET PUT DELETE
     */
    public static final String QUERY_UPDATE_DELETE = "https://api2.bmob.cn/1/classes/";

    /**
     * POST
     */
    public static final String UPLOAD_FILE = "https://api2.bmob.cn/2/files/";

    /**
     * GET
     */
    public static final String _USER = "https://api2.bmob.cn/1/users";

    private static OkHttpClient CLIENT = new OkHttpClient();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType JPEG = MediaType.parse("image/jpeg");

    public static final Builder BUILDER = new Request.Builder()
            .addHeader("X-Bmob-Application-Id", X_Bmob_Application_Id)
            .addHeader("X-Bmob-REST-API-Key", X_Bmob_REST_API_Key).addHeader("Content-Type", "application/json");

    public <T> String makeBeanToString(T t) {
        return JSONObject.toJSONString(t);
    }

    public Ret toLogin(String username, String password) {
        Request request = BUILDER.url(LOGIN_URL + "?username=" + username + "&password=" + password)
                .get().build();
        try (Response response = CLIENT.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                return Ret.fail();
            }
            LOGGER.info(responseBody.string());
            Ret msg = response.code() == 200 ? Ret.ok() : Ret.fail();
            msg.set("value", responseBody.string());
            return msg;
        } catch (Exception e) {
            LOGGER.info("BmobKit - toLogin : " + e.getMessage());
        }
        return Ret.fail();
    }

    public <T> List<T> getListFromBmob(Class<T> clazz, String... include) {
        String url = QUERY_UPDATE_DELETE + clazz.getSimpleName().toLowerCase() +
                BmobUtilsKit.INSTANCE.analInclude(include);
        Request request = BUILDER.url(url).get().build();
        try (Response response = CLIENT.newCall(request).execute()) {

            String text = response.body() != null ? response.body().string() : null;
            LOGGER.info(text);
            JSONObject json = JSONObject.parseObject(text);
            return JSONObject.parseArray(json.get("results").toString(), clazz);
        } catch (Exception e) {
            LOGGER.info("BmobKit - getListFromBmob : " + e.getMessage());
            return null;
        }
    }

    public <T> List<T> getListFromBmobWithWhere(Class<T> clazz, JSONObject object) {
        String url = QUERY_UPDATE_DELETE + clazz.getSimpleName().toLowerCase() +
                BmobUtilsKit.INSTANCE.analWhere(object);
        Request request = BUILDER.url(url).get().build();
        try (Response response = CLIENT.newCall(request).execute()) {
            String text = response.body() != null ? response.body().string() : null;
            LOGGER.info(text);
            JSONObject json = JSONObject.parseObject(text);
            return JSONObject.parseArray(json.get("results").toString(), clazz);
        } catch (Exception e) {
            LOGGER.info("BmobKit - getListFromBmobWithWhere : " + e.getMessage());
            return null;
        }
    }

    public <T> List<T> getUserListFromBmob(Class<T> clazz) {
        Request request = BUILDER.url(_USER).get().build();
        try (Response response = CLIENT.newCall(request).execute()) {
            String text = response.body() != null ? response.body().string() : null;
            LOGGER.info(text);
            JSONObject json = JSONObject.parseObject(text);
            return JSONObject.parseArray(json.get("results").toString(), clazz);
        } catch (Exception e) {
            LOGGER.info("BmobKit - getUserListFromBmob : " + e.getMessage());
            return null;
        }
    }

    public <T> T getOneUserFromBmob(Class<T> clazz, String objectId) {
        String url = _USER + "/" + objectId;
        Request request = BUILDER.url(url).get().build();
        try (Response response = CLIENT.newCall(request).execute()) {
            String text = response.body() != null ? response.body().string() : null;
            LOGGER.info(text);
            return JSONObject.parseObject(text, clazz);
        } catch (Exception e) {
            LOGGER.info("BmobKit - getOneUserFromBmob : " + e.getMessage());
            return null;
        }
    }

    public <T> T getOneBeanFromBmob(Class<T> clazz, String objectId, String... include) {
        String url = QUERY_UPDATE_DELETE + clazz.getSimpleName().toLowerCase() + "/" + objectId
                + BmobUtilsKit.INSTANCE.analInclude(include);
        Request request = BUILDER.url(url).get().build();
        try (Response response = CLIENT.newCall(request).execute()) {
            String text = response.body() != null ? response.body().string() : null;
            LOGGER.info(text);
            return JSONObject.parseObject(text, clazz);
        } catch (Exception e) {
            LOGGER.info("BmobKit - getOneFromBmob : " + e.getMessage());
            return null;
        }

    }

    public String upLoadFileToBmob(UploadFile uploadFile) {
        String url = UPLOAD_FILE + uploadFile.getFileName();
        RequestBody r = RequestBody.create(JPEG, uploadFile.getFile());
        Request request = BUILDER.url(url).post(r).build();
        try (Response response = CLIENT.newCall(request).execute()) {
            String text = response.body() != null ? response.body().string() : null;
            LOGGER.info(text);
            return JSONObject.parseObject(text).get("url").toString();
        } catch (Exception e) {
            LOGGER.info("BmobKit - upLoadFileToBmob : " + e.getMessage());
            return null;
        }

    }

    public <T> boolean updateBeanToBmob(T t, String objectId) {
        String url = QUERY_UPDATE_DELETE + t.getClass().getSimpleName().toLowerCase() + "/" + objectId;
        if (t instanceof User) {
            url = QUERY_UPDATE_DELETE + "_User/" + objectId;
            BUILDER.addHeader("X-Bmob-Master-Key", X_Bmob_Master_Key);
        }
        RequestBody r = RequestBody.create(JSON, makeBeanToString(t));
        Request request = BUILDER.url(url).put(r).build();
        try (Response response = CLIENT.newCall(request).execute()) {
            String text = response.body() != null ? response.body().string() : null;
            LOGGER.info(text);
            return StrKit.notBlank(JSONObject.parseObject(text).get("updatedAt").toString());
        } catch (Exception e) {
            LOGGER.info("BmobKit - updateBeanToBmob : " + e.getMessage());
            return false;
        }

    }

    public <T> boolean addBeanToBmob(T t) {
        String url = QUERY_UPDATE_DELETE + t.getClass().getSimpleName().toLowerCase();
        if (t instanceof User) {
            url = QUERY_UPDATE_DELETE + "_User/";
            BUILDER.addHeader("X-Bmob-Master-Key", X_Bmob_Master_Key);
        }
        RequestBody r = RequestBody.create(JSON, makeBeanToString(t));
        Request request = BUILDER.url(url).post(r).build();
        try (Response response = CLIENT.newCall(request).execute()) {
            String text = response.body() != null ? response.body().string() : null;
            LOGGER.info(text);
            return StrKit.notBlank(JSONObject.parseObject(text).get("createdAt").toString());
        } catch (Exception e) {
            LOGGER.info("BmobKit - addBeanToBmob : " + e.getMessage());
            return false;
        }
    }

    public <K> boolean addRelationToBmob(Class<K> clazz, String objectId, String value) {
        String url = QUERY_UPDATE_DELETE + clazz.getSimpleName().toLowerCase() + "/" + objectId;
        RequestBody r = RequestBody.create(JSON, value);
        Request request = BUILDER.url(url).put(r).build();
        try (Response response = CLIENT.newCall(request).execute()) {
            String text = response.body() != null ? response.body().string() : null;
            LOGGER.info(text);
            return StrKit.notBlank(JSONObject.parseObject(text).get("msg").toString());
        } catch (Exception e) {
            LOGGER.info("BmobKit - addRelationToBmob : " + e.getMessage());
            return false;
        }
    }

    public <T> boolean deleteToBmob(Class<T> clazz, String objectId) {
        String url = QUERY_UPDATE_DELETE + clazz.getSimpleName().toLowerCase() + "/" + objectId;
        Request request = BUILDER.url(url).delete().build();
        try (Response response = CLIENT.newCall(request).execute()) {
            String text = response.body() != null ? response.body().string() : null;
            LOGGER.info(text);
            return StrKit.notBlank(JSONObject.parseObject(text).get("msg").toString());
        } catch (Exception e) {
            LOGGER.info("BmobKit - deleteToBmob : " + e.getMessage());
            return false;
        }
    }
}
