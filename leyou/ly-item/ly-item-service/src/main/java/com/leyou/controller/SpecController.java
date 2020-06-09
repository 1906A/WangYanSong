package com.leyou.controller;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupService;
import com.leyou.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    SpecGroupService specGroupService;

    @Autowired
    SpecParamService specParamService;




    /**
     * 添加,修改商品组信息
     * @param specGroup
     */
    @RequestMapping("group")
    public void addSpecGroup(@RequestBody SpecGroup specGroup){
        if(specGroup.getId()==null){
            specGroupService.addSpecGroup(specGroup);
        }else {
            specGroupService.updateSpecGroup(specGroup);
        }
    }

    /**
     * 根据cid查询商品规格组信息
     * @param cid
     * @return
     */
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findAllSpecGroup(@PathVariable("cid") Long cid){
        return specGroupService.findAllSpecGroup(cid);
    }

    /**
     * 根据cid查询商品规格组信息
     * @param cid
     * @return
     */
    @RequestMapping("groupsbycid/{cid}")
    public List<SpecGroup> findAllSpecGroupAndSpecParamByCid(@PathVariable("cid") Long cid){
        return specGroupService.findAllSpecGroupAndSpecParamByCid(cid);
    }

    /**
     * 根据id删除商品规格表
     * @param id
     */
    @RequestMapping("group/{id}")
    public void deleteSpecGroupById(@PathVariable("id") Long id){
        specGroupService.deleteSpecGroupById(id);
    }








    /**
     * 展示商品规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public List<SpecParam> findAllParams(@RequestParam(value = "gid",required = false)Long gid){


       List<SpecParam> list= specParamService.findAllParams(gid);
       return list;
    }


    /**
     * 添加或修改商品规格参数
     * @param specParam
     */
    @RequestMapping("param")
    public void addSpecParam(@RequestBody SpecParam specParam){
        if(specParam.getId()==null){
            specParamService.addSpecParam(specParam);
        }else{
            specParamService.updateSpecParam(specParam);
        }
    }


    /**
     * 根据id删除参数
     * @param id
     */
    @RequestMapping("param/{id}")
    public void deleteBySpecParamId(@PathVariable("id")Long id){
        specParamService.deleteBySpecParamId(id);
    }


    /**
     * 根据分类id 查询规格参数
     * @param cid
     * @return
     */
    @RequestMapping("paramsByCid")
    public List<SpecParam>  findSpecParamByCid(@RequestParam("cid")Long cid){

      List<SpecParam> specParamList=  specParamService.findParamByCid(cid);

      return  specParamList;
    }

    /**
     * 根据分类id 查询规格参数，查询出可搜素的字段
     * @param cid
     * @return
     */
    @RequestMapping("paramByCid")
    public List<SpecParam>  findSpecParamsByCid1(@RequestParam("cid")Long cid){

        List<SpecParam> specParamList=  specParamService.findSpecParamsByCid1(cid);

        return  specParamList;
    }


    /**
     * 根据分类id 查询规格参数，查询出通用参数的字段,用于商品详情页的查询
     * @param cid
     * @return
     */
    @RequestMapping("findSpecParamsByCidAndGeneric")
    public List<SpecParam>  findSpecParamsByCidAndGeneric(@RequestParam("cid")Long cid,@RequestParam("generic") boolean gneric){

        List<SpecParam> specParamList=  specParamService.findSpecParamsByCidAndGeneric(cid,gneric);

        return  specParamList;
    }


}
