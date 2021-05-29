package com.http.study;

import java.util.List;

import lombok.Data;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-28
 * @Description
 */
@Data
public class MessageVO {

    private int status;
    private String message;
    private Datas data;

}

@Data
class Datas {
    private List<PoiSearchDataVO> searchDataVOs;
}

@Data
class PoiSearchDataVO {
    private PoiSearchDatailVO kuaiShouPoi;
}

@Data
class PoiSearchDatailVO {
    private String poiId;
}