package com.gillion.repository;

import com.gillion.ds.client.api.DaoServiceClient;
import com.gillion.model.entity.DictAreaInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gillion.model.querymodels.QDictAreaInfo.dictAreaInfo;

/**
 * 地区信息(DictAreaInfo)数据存储类
 *
 * @author liaowj
 * @since 2019-10-23 23:05:26
 */
@SuppressWarnings("WeakerAccess")
@Repository("dictAreaInfoRepository}")
public class DictAreaInfoRepository {


    public static final String DS_QUICKSTART_DCIT_AREA_INFO_QUERY_ALL="DS_QUICKSTART#DICT_AREA_INFO#QUERY_ALL";

    private final DaoServiceClient client;

    public DictAreaInfoRepository(@Qualifier("embedEngineClient") DaoServiceClient client) {
        this.client = client;
    }

    public List<DictAreaInfo> queryAll(){
        return client.withModel(dictAreaInfo)
                .select(dictAreaInfo.fieldContainer())
                .tag(DS_QUICKSTART_DCIT_AREA_INFO_QUERY_ALL)
                .execute();
    }

}