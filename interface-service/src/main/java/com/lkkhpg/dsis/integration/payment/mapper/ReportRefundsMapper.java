package com.lkkhpg.dsis.integration.payment.mapper;

import com.lkkhpg.dsis.integration.payment.dto.ReportRefunds;

import java.util.List;

/**
 * Created by hand on 2018/6/6.
 */
public interface ReportRefundsMapper {

   List<ReportRefunds>reportRefunds(ReportRefunds reportRefunds);
}
