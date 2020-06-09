package com.leyou.service;

import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.Category;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecParamService {

    @Autowired
    SpecParamMapper specParamMapper;

    public List<SpecParam> findAllParams(Long groupId) {
        SpecParam specParam=new SpecParam();
        specParam.setGroupId(groupId);
        return  specParamMapper.select(specParam);
        //return  specParamMapper.findAllParams(groupId);
    }

    public void addSpecParam(SpecParam specParam) {

        specParamMapper.addSpecParam(specParam);
    }

    public void updateSpecParam(SpecParam specParam) {
        specParamMapper.updateByPrimaryKey(specParam);
    }

    public void deleteBySpecParamId(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }

    public List<SpecParam> findParamByCid(Long cid) {

        return specParamMapper.findParamByCid(cid);
    }

    public List<SpecParam> findSpecParamsByCid1(Long cid) {

        SpecParam specParam=new SpecParam();
        specParam.setCid(cid);
        specParam.setSearching(true);

        return specParamMapper.select(specParam);
    }

    public List<SpecParam> findSpecParamsByCidAndGeneric(Long cid, boolean gneric) {
        SpecParam specParam=new SpecParam();
        specParam.setCid(cid);
        specParam.setGeneric(gneric);
        return specParamMapper.select(specParam);
    }
}
