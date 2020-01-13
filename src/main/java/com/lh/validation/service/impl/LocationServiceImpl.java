package com.lh.validation.service.impl;

import com.alibaba.fastjson.JSON;
import com.lh.validation.dto.LCRestResult;
import com.lh.validation.entity.LocationPoint;
import com.lh.validation.mapper.LocationPointMapper;
import com.lh.validation.service.LocationService;
import com.lh.validation.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


/**
 * @Author liuhh    @Date 2019/12/13 11:19
 */
@Slf4j
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LocationPointMapper locationPointMapper;

    private String historyTrackUrl = "http://119.3.215.239:8090/rprest/location/trackReplay";

    public void historyTrack() {

        Map<String,Object> requestJson = new HashMap<String, Object>();
        requestJson.put("resultType", 2);
//        requestJson.put("beginDate",   1575860869L);
//        requestJson.put("endDate",   1576050367L);

//        requestJson.put("beginDate",   1576050368L);
//        requestJson.put("endDate",   1576250368L);

        requestJson.put("beginDate",   1576250369L);
        requestJson.put("endDate",   1576450368L);

//        requestJson.put("beginDate",   1576450369L);
//        requestJson.put("endDate",   1576650368L);

        requestJson.put("terminalId", 10363800004L);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(JSON.toJSONString(requestJson), headers);
        ResponseEntity<LCRestResult> restResultResponseEntity = this.restTemplate.exchange(
                this.historyTrackUrl,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<LCRestResult>() {
                }
        );

        if(restResultResponseEntity.getStatusCode().value() == HttpStatus.OK.value()){
            LCRestResult lcRestResult = restResultResponseEntity.getBody();
            if(lcRestResult.getResultCode() == 200){
                List<Map<String,Object>> data = (List<Map<String, Object>>) lcRestResult.getData();
                List<LocationPoint> locationPoints = new ArrayList<>();
                if (Objects.nonNull(data)) {
                    data.stream().forEach(e -> {
                        Integer load = (Integer)e.get("dynamicLoad");
                        LocationPoint dto = LocationPoint.builder()
                                .speed((Integer) e.get("speed"))
                                .direction((Integer) e.get("direction"))
                                .latitude((Integer)e.get("latitude"))
                                .longitude((Integer)e.get("longitude"))
                                .dynamicLoad((load/100)+"."+(load%100))
                                .gpsDate((Integer)e.get("gpsDate"))
                                .gpsDateStr(DateUtil.timeToDateStr(Long.parseLong(e.get("gpsDate").toString())) )
                                .build();
                        locationPoints.add(dto);
                    });
                    locationPointMapper.insertList(locationPoints);
                }
            }else{
                log.error("请求失败，消息：{}",lcRestResult.getMessage());
            }
        }else{
            log.error("位置云服务异常");
        }

    }
}
