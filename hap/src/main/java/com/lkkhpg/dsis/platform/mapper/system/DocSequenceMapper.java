/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import com.lkkhpg.dsis.platform.dto.system.DocSequence;

/**
 * @author wuyichu
 */
public interface DocSequenceMapper {

    DocSequence lockDocSequence(DocSequence docSequence);

    int insert(DocSequence docSequence);

    int update(DocSequence docSequence);
}