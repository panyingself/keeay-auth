
package com.keeay.anepoch.auth.biz.interfacewhitelistinfo;

import com.keeay.anepoch.auth.biz.interfacewhitelistinfo.bo.*;

import java.util.List;


/**
 * @author AI Admin
 */
public interface InterfaceWhiteListInfoBiz {
    /**
     * 新增 record
     *
     * @param addInterfaceWhiteListInfoBo addInterfaceWhiteListInfoBo
     * @return success true orElse false
     */
    boolean add(InterfaceWhiteListInfoBo addInterfaceWhiteListInfoBo);

    /**
     * 修改 record
     *
     * @param editInterfaceWhiteListInfoBo editInterfaceWhiteListInfoBo
     * @return success true orElse false
     */
    boolean editById(InterfaceWhiteListInfoBo editInterfaceWhiteListInfoBo);

    /**
     * 查询record集合
     *
     * @param queryInterfaceWhiteListInfoBo queryInterfaceWhiteListInfoBo
     * @return record list
     */
    List<InterfaceWhiteListInfoBo> list(InterfaceWhiteListInfoBo queryInterfaceWhiteListInfoBo);

    /**
     * 获取所有白名单
     *
     * @return list
     */
    List<String> getAllWhiteList();

    /**
     * 查询record detail
     *
     * @param recordId recordId
     * @return record detail
     */
    InterfaceWhiteListInfoBo fetchDetailById(Long recordId);
}

