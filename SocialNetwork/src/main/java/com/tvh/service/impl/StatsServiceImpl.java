package com.tvh.service.impl;

import com.tvh.repository.StatsRepository;
import com.tvh.service.StatsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hoangtrinh
 */
@Service
public class StatsServiceImpl implements StatsService{

    @Autowired
    private StatsRepository statsRepo;

    @Override
    public List<Object[]> userStats(Map<String, String> params) {
        return this.statsRepo.userStats(params);
    }

    @Override
    public List<Object[]> postStats(Map<String, String> params) {
        return this.statsRepo.postStats(params);
    }
    
}
