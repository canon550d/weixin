package org.hb0712.discovery.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Blog;
import org.hb0712.discovery.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;

    @RequestMapping("/blog/index")
    public String index(Map<String,Object> model, Page page){
        if(page==null) {
            page = new Page();
        }
        model.put("page", page);

        List<Blog> blogs = blogService.list(page);
        model.put("blogs", blogs);

        return "blog/index";
    }

    @ResponseBody
    @RequestMapping("/blog/list")
    public String list(Page page){
        JsonObject model = new JsonObject();
        if(page==null) {
            page = new Page();
        }
        model.add("page", getPage(page));

        List<Blog> blogs = blogService.list(page);
        model.add("blogs", getBlogs(blogs));

        return model.toString();
    }

    private JsonObject getPage(Page page){
        JsonObject json = new JsonObject();
        json.addProperty("pageSize", page.getPageSize());
        json.addProperty("page", page.getPage());
        json.addProperty("last", page.getLast());
        json.addProperty("total", page.getTotal());
        json.addProperty("next", page.getNext());
        json.addProperty("previous", page.getPrevious());
        return json;
    }

    private JsonArray getBlogs(List<Blog> blogs){
        JsonArray array = new JsonArray();
        for (Blog b:blogs){
            array.add(getBlog(b));
        }
        return array;
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private JsonObject getBlog(Blog blog){
        JsonObject json = new JsonObject();
        json.addProperty("id", blog.getId());
        json.addProperty("note", blog.getNote());
        json.addProperty("time", format.format(blog.getTime()));
        json.add("media", getMedia(blog.getJsonobject()));
        return json;
    }

    private JsonArray getMedia(String media){
        if (StringUtils.isEmpty(media)){
            return null;
        }
        JsonObject json = new JsonParser().parse(media).getAsJsonObject();
        return json.getAsJsonArray("data");
    }
}
