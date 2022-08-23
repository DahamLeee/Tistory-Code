package com.wangtak.pagemarker.domain;

import com.wangtak.pagemarker.application.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    Page<MemberDto> search(Pageable pageable);
}
