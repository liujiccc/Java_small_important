package cn.itcast.service;

import cn.itcast.domain.Province;

import java.util.List;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/12/9 9:05
 */
public interface ProvinceService {
    public List<Province> findAll();
    public String findAllJson();
}
