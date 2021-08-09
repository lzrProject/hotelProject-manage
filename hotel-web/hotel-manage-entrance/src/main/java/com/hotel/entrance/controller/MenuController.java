package com.hotel.entrance.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotel.index.feign.IndexFeign;
import com.hotel.index.pojo.Menu;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping(value = "/power/user")
public class MenuController {

    @Autowired
    private IndexFeign indexFeign;

    @GetMapping("menu")
    public Object getUserMenu(){
        List<Menu> result = indexFeign.getUserMenu();
//        JSONObject object1 = new JSONObject();
//        JSONArray object = new JSONArray();
//        Map keyMap = new HashMap();
//        keyMap.put("powerName","title");
//        keyMap.put("powerType","type");
//        keyMap.put("powerUrl","href");
//        for(int i = 0;i < result.size();i++){
//            if(result.get(i).getEnable().equals(1)) {
//                object1.put("0", result.get(i));
//                JSONObject jsonObject = changeJsonObj(object1, keyMap);
//                object.add(i, jsonObject.get(0));
//            }
//        }

        return result;
    }



    /**
     *
     * 对json数据key进行替换
     * @author
     *
     */
    public static JSONObject changeJsonObj(JSONObject jsonObj,Map<String, String> keyMap) {


        JSONObject resJson = new JSONObject(true);
        Set<String> keySet = jsonObj.keySet();
        for (String key : keySet) {
            String resKey = keyMap.get(key) == null ? key : keyMap.get(key);
            try {
                JSONObject jsonobj1 = jsonObj.getJSONObject(key);
                resJson.put(resKey, changeJsonObj(jsonobj1, keyMap));
            } catch (Exception e) {
                try {
                    JSONArray jsonArr = jsonObj.getJSONArray(key);
                    resJson.put(resKey, changeJsonArr(jsonArr, keyMap));
                } catch (Exception x) {
                    resJson.put(resKey, jsonObj.get(key));
                }
            }
        }
        return resJson;
    }

    public static JSONArray changeJsonArr(JSONArray jsonArr,Map<String, String> keyMap) {
        JSONArray resJson = new JSONArray();
        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            resJson.add(changeJsonObj(jsonObj, keyMap));
        }
        return resJson;
    }
}
