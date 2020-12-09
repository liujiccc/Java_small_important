package cn.itcast.dao;

import cn.itcast.domain.Province;

import java.util.List;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/12/9 9:19
 */
public interface ProvinceDao {
    public List<Province> findAll();
}
