package com.hotel.wx.service.impl;

import com.hotel.wx.dao.CustomerMapper;
import com.hotel.wx.pojo.Customer;
import com.hotel.wx.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void addAll(Customer customer) {
        customerMapper.insertSelective(customer);
    }

    @Override
    public List<Customer> findByOpenId(String openId) {
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("openId",openId);
        return customerMapper.selectByExample(example);
    }
}
