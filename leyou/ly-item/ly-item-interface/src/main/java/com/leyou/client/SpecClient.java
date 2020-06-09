package com.leyou.client;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("spec")
public interface SpecClient {

    @RequestMapping("paramByCid")
    public List<SpecParam> findSpecParamsByCid1(@RequestParam("cid")Long cid);

    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findAllSpecGroup(@PathVariable("cid") Long cid);

    @RequestMapping("findSpecParamsByCidAndGeneric")
    public List<SpecParam>  findSpecParamsByCidAndGeneric(@RequestParam("cid")Long cid,@RequestParam("generic") boolean gneric);

    @RequestMapping("groupsbycid/{cid}")
    public List<SpecGroup> findAllSpecGroupAndSpecParamByCid(@PathVariable("cid") Long cid);
}
