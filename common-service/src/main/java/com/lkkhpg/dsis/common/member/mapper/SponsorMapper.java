/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.member.dto.Sponsor;

public interface SponsorMapper {
    List<Sponsor> selectSponsor(Sponsor record);

    Sponsor getValidSponsorBySponsorNo(Sponsor record);
}