/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author hoangtrinh
 */
@Service
public interface StatsService {
//    List<Object[]> countUser();
    List<Object[]> userStats(Map<String, String> params);
    List<Object[]> postStats(Map<String, String> params);

//    List<Object[]> countBookedTicketByRoutes();
//    List<Object[]> statesRevenueTotal(Map<String, String> params);
}
