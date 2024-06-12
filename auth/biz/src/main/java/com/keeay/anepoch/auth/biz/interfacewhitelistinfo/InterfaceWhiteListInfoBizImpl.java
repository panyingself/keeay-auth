
package com.keeay.anepoch.auth.biz.interfacewhitelistinfo;

import com.keeay.anepoch.base.commons.lang.Safes;
import com.keeay.anepoch.base.commons.utils.ConditionUtils;
import com.keeay.anepoch.auth.service.model.*;
import com.keeay.anepoch.auth.biz.interfacewhitelistinfo.bo.*;
import com.keeay.anepoch.auth.service.service.interfacewhitelistinfo.InterfaceWhiteListInfoService;
import com.keeay.anepoch.auth.biz.interfacewhitelistinfo.converter.InterfaceWhiteListInfoBoConverter;
import com.keeay.anepoch.base.commons.monitor.BaseBizTemplate;
import com.keeay.anepoch.base.commons.utils.JsonMoreUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author AI Admin
 */
@Service
@Slf4j
public class InterfaceWhiteListInfoBizImpl implements InterfaceWhiteListInfoBiz {
    @Resource
    private InterfaceWhiteListInfoService interfaceWhiteListInfoService;

    /**
     * 新增 record
     *
     * @param addInterfaceWhiteListInfoBo addInterfaceWhiteListInfoBo
     * @return success true orElse false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(InterfaceWhiteListInfoBo addInterfaceWhiteListInfoBo) {
        return new BaseBizTemplate<Boolean>() {
            @Override
            protected void checkParam() {
                ConditionUtils.checkArgument(Objects.nonNull(addInterfaceWhiteListInfoBo), "addInterfaceWhiteListInfoBo is null");
            }

            @Override
            protected Boolean process() {
                //新增角色信息
                InterfaceWhiteListInfo newInterfaceWhiteListInfo = InterfaceWhiteListInfoBoConverter.convertToInterfaceWhiteListInfo(addInterfaceWhiteListInfoBo);
                interfaceWhiteListInfoService.insert(newInterfaceWhiteListInfo);
                return true;
            }
        }.execute();
    }

    /**
     * 修改 record
     *
     * @param editInterfaceWhiteListInfoBo editInterfaceWhiteListInfoBo
     * @return success true orElse false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editById(InterfaceWhiteListInfoBo editInterfaceWhiteListInfoBo) {
        return new BaseBizTemplate<Boolean>() {
            @Override
            protected void checkParam() {
                ConditionUtils.checkArgument(Objects.nonNull(editInterfaceWhiteListInfoBo), "editInterfaceWhiteListInfoBo is null");
                ConditionUtils.checkArgument(Objects.nonNull(editInterfaceWhiteListInfoBo.getId()), "editInterfaceWhiteListInfoBo id is null");
            }

            @Override
            protected Boolean process() {
                InterfaceWhiteListInfo oldInterfaceWhiteListInfo = interfaceWhiteListInfoService.getDetailById(editInterfaceWhiteListInfoBo.getId());
                ConditionUtils.checkArgument(Objects.nonNull(oldInterfaceWhiteListInfo), "oldInterfaceWhiteListInfo is null");
                //修改记录
                InterfaceWhiteListInfo waitToUpdate = InterfaceWhiteListInfoBoConverter.convertToInterfaceWhiteListInfo(editInterfaceWhiteListInfoBo);
                interfaceWhiteListInfoService.update(waitToUpdate);
                return true;
            }
        }.execute();
    }

    /**
     * 查询record集合
     *
     * @param queryInterfaceWhiteListInfoBo queryInterfaceWhiteListInfoBo
     * @return record list
     */
    @Override
    public List<InterfaceWhiteListInfoBo> list(InterfaceWhiteListInfoBo queryInterfaceWhiteListInfoBo) {
        return new BaseBizTemplate<List<InterfaceWhiteListInfoBo>>() {
            @Override
            protected void checkParam() {
                ConditionUtils.checkArgument(Objects.nonNull(queryInterfaceWhiteListInfoBo), "queryInterfaceWhiteListInfoBo is null");
            }

            @Override
            protected List<InterfaceWhiteListInfoBo> process() {
                InterfaceWhiteListInfo interfaceWhiteListInfoQuery = InterfaceWhiteListInfoBoConverter.convertToInterfaceWhiteListInfo(queryInterfaceWhiteListInfoBo);
                List<InterfaceWhiteListInfo> fromDbList = interfaceWhiteListInfoService.list(interfaceWhiteListInfoQuery);
                if (CollectionUtils.isEmpty(fromDbList)) {
                    return Lists.newArrayList();
                }
                return JsonMoreUtils.ofList(JsonMoreUtils.toJson(fromDbList), InterfaceWhiteListInfoBo.class);
            }
        }.execute();
    }

    /**
     * 获取所有白名单
     *
     * @return list
     */
    @Override
    public List<String> getAllWhiteList() {
        return new BaseBizTemplate<List<String>>() {
            @Override
            protected List<String> process() {
                List<InterfaceWhiteListInfoBo> interfaceWhiteListInfoBoList = list(new InterfaceWhiteListInfoBo());
                return Safes.of(interfaceWhiteListInfoBoList).stream()
                        .map(InterfaceWhiteListInfoBo::getInterfaceUrl)
                        .collect(Collectors.toList());
            }
        }.execute();
    }

    /**
     * 查询record
     *
     * @param recordId recordId
     * @return record orElse null
     */
    @Override
    public InterfaceWhiteListInfoBo fetchDetailById(Long recordId) {
        return new BaseBizTemplate<InterfaceWhiteListInfoBo>() {
            @Override
            protected void checkParam() {
                ConditionUtils.checkArgument(Objects.nonNull(recordId), "recordId is null");
            }

            @Override
            protected InterfaceWhiteListInfoBo process() {
                InterfaceWhiteListInfo fromDb = interfaceWhiteListInfoService.getDetailById(recordId);
                if (Objects.isNull(fromDb)) {
                    return null;
                }
                return JsonMoreUtils.toBean(JsonMoreUtils.toJson(fromDb), InterfaceWhiteListInfoBo.class);
            }
        }.execute();
    }
}

