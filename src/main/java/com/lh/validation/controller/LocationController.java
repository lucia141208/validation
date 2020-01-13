package com.lh.validation.controller;

import com.lh.validation.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liuhh    @Date 2019/12/13 11:17
 */
@RestController
@RequestMapping("lcoation")
public class LocationController {

 @Autowired
    private LocationService locationService;

 @GetMapping(value = "historyTrack")
    public void test(){
        locationService.historyTrack();
    }

}
