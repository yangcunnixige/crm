package com.yangnan.crm.rbac.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConvertUtil {
    private static Logger LOG = LoggerFactory.getLogger(ConvertUtil.class);
    private ConvertUtil() {
    }

    public static <S, T> T convert(S sourceObject, Class<T> targetClass) {
        if (Objects.isNull(sourceObject)) {
            return null;
        }
        T targetObject = null;
        try {
            targetObject = targetClass.newInstance();
        } catch (Exception e) {
            LOG.error("object convert error : {}", e.getMessage());
        }
        BeanUtils.copyProperties(sourceObject, targetObject);
        return targetObject;
    }

    public static <S, T> ArrayList<T> convertList(List<S> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        ArrayList<T> targetList = new ArrayList<>();
        for (int index = 0; index < sourceList.size(); index++) {
            T targetObject = null;
            try {
                targetObject = targetClass.newInstance();
            } catch (Exception e) {
                LOG.error("object convert error : {}", e.getMessage());
            }
            BeanUtils.copyProperties(sourceList.get(index),targetObject);
            targetList.add(targetObject);
        }
        return targetList;
    }
}