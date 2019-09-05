package com.fumiya.springboot;

import com.fumiya.springboot.repositories.MyDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class HeloController {

    @Autowired
    MyDataRepository repository;

    @Autowired
    MyDataBean myDataBean;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView indexById(@PathVariable long id, ModelAndView mav) {
        mav.setViewName("pickup");
        mav.addObject("title", "Pickup Page");
        String table = "<table>"
                + myDataBean.getTableTagById(id)
                + "</table>";
        mav.addObject("msg", "pickup data id = " + id);
        mav.addObject("data", table);
        return mav;
    }

    @Autowired
    private MyDataService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("index");
        mav.addObject("msg", "MyDataDaoのサンプルです");
        Iterable<MyData> list = service.getAll();
        mav.addObject("datalist", list);
        return mav;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView find(ModelAndView mav) {
        mav.setViewName("find");
        mav.addObject("title", "Find Page");
        mav.addObject("msg", "MyDataのサンプルです。");
        mav.addObject("value", "");
        List<MyData> list = service.getAll();
        mav.addObject("datalist", list);
        return mav;
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request, ModelAndView mav) {
        mav.setViewName("find");
        String param = request.getParameter("fstr");
        if(param == "") {
            mav = new ModelAndView("redirect:/find");
        } else {
            mav.addObject("title", "Find result");
            mav.addObject("msg", "[" + param + "]の検索結果");
            mav.addObject("value", param);
            List<MyData> list = service.find(param);
            mav.addObject("datalist", list);
        }
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@ModelAttribute MyData mydata, @PathVariable int id, ModelAndView mav) {
        mav.setViewName("edit");
        mav.addObject("title", "edit mydata.");
        Optional<MyData> data = repository.findById((long)id);
        mav.addObject("formModel", data.get());
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ModelAndView update(@ModelAttribute MyData mydata, ModelAndView mav) {
        repository.saveAndFlush(mydata);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id, ModelAndView mav) {
        mav.setViewName("delete");
        mav.addObject("title", "delete mydata");
        Optional<MyData> data = repository.findById((long) id);
        mav.addObject("formModel", data.get());
        return mav;
    }

    @RequestMapping(value="/delete", method=RequestMethod.POST)
    @Transactional(readOnly = false)
    public ModelAndView remove(@RequestParam long id, ModelAndView mav) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/");
    }

    @PostConstruct
    public void init() {
        MyData d1 = new MyData();
        d1.setName("fumiya");
        d1.setAge(24);
        d1.setMail("fumiya@fumiya.com");
        d1.setMemo("090999999");
        repository.saveAndFlush(d1);

        MyData d2 = new MyData();
        d2.setName("hanako");
        d2.setAge(15);
        d2.setMail("hanako@flower");
        d2.setMemo("080888888");
        repository.saveAndFlush(d2);

        MyData d3 = new MyData();
        d3.setName("sachiko");
        d3.setAge(37);
        d3.setMail("sachiko@happy");
        d3.setMemo("070777777");
        repository.saveAndFlush(d3);
    }
}
