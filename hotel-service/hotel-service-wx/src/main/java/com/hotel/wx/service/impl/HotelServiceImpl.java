package com.hotel.wx.service.impl;

import com.github.pagehelper.PageInfo;
import com.hotel.wx.dao.HotelFileMapper;
import com.hotel.wx.dao.HotelMapper;
import com.hotel.wx.pojo.Hotel;
import com.hotel.wx.pojo.HotelFile;
import com.hotel.wx.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private HotelFileMapper hotelFileMapper;

    @Override
    public void addAll(Hotel hotel) {
        hotelMapper.insertSelective(hotel);
    }

    @Override
    public void addAll(HotelFile hotelFile) {
        hotelFileMapper.insertSelective(hotelFile);
    }

    @Override
    public void delById(Integer id) {
        hotelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delByHotelId(Integer id) {
        Example example = new Example(HotelFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("hotelId",id);
        hotelFileMapper.deleteByExample(example);
    }

    @Override
    public void update(Hotel hotel) {
        hotelMapper.updateByPrimaryKeySelective(hotel);
    }

    @Override
    public Hotel findById(int id) {
        return hotelMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Hotel> findByParentId(int parentId) {
        Example example = new Example(Hotel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId",parentId);
        return hotelMapper.selectByExample(example);
    }

    @Override
    public List<Hotel> findList(Hotel hotel) {
        return hotelMapper.selectList(hotel);
    }

    @Override
    public List<Hotel> findAll() {
        return hotelMapper.selectAll();
    }

    @Override
    public PageInfo<Hotel> findPage(int page, int size) {
        return null;
    }

    @Override
    public PageInfo<Hotel> findPage(Hotel hotel, int page, int size) {
        return null;
    }

    @Override
    public List<Hotel> toUserMenu(List<Hotel> sysMenus, Integer parentId) {
        return null;
    }
}
