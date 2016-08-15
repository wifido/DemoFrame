package com.sf.sfpp.pcomp.manager;

import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.dao.PcompTitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/15
 */
@Component
public class PcompTitleManager {
    @Autowired
    private PcompTitleMapper pcompTitleMapper;


    public List<PcompTitle> getAllTitles() {
        return pcompTitleMapper.selectAllAvailable();
    }
}
