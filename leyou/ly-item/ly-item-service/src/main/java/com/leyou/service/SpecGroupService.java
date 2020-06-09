package com.leyou.service;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecGroupService {

    @Autowired
    SpecGroupMapper specGroupMapper;

    @Autowired
    SpecParamMapper specParamMapper;


    public void addSpecGroup(SpecGroup specGroup) {
        specGroupMapper.addSpecGroup(specGroup);
    }

    public List<SpecGroup> findAllSpecGroup(Long cid) {

        return  specGroupMapper.findAllSpecGroup(cid);
    }


    public void deleteSpecGroupById(Long id) {

         specGroupMapper.deleteByPrimaryKey(id);
    }

    public void updateSpecGroup(SpecGroup specGroup) {

        specGroupMapper.updateByPrimaryKeySelective(specGroup);
    }

    public List<SpecGroup> findAllSpecGroupAndSpecParamByCid(Long cid) {

        SpecGroup specGroup=new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> groupList = specGroupMapper.select(specGroup);
        groupList.forEach(gorup->{
            SpecParam specParam=new SpecParam();
            specParam.setGroupId(gorup.getId());
            List<SpecParam> params = specParamMapper.select(specParam);
            gorup.setSpecParamList(params);
        });
        return  groupList;
    }
}
