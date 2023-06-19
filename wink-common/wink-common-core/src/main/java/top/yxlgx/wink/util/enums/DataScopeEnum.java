/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package top.yxlgx.wink.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yanxin
 * @Description: 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {

    /* 全部的数据权限 */
    ALL("1", "全部的数据权限"),

    /* 自己部门的数据权限 */
    CUSTOMIZE("2", "自定数据权限"),

    /* 自定义的数据权限 */
    SELF_DEPT("3", "本部门数据权限"),
    SELF_DEPT_AND_LOWER("4", "本部门及以下数据权限");

    private final String value;
    private final String description;

    public static DataScopeEnum find(String val) {
        for (DataScopeEnum dataScopeEnum : DataScopeEnum.values()) {
            if (dataScopeEnum.getValue().equals(val)) {
                return dataScopeEnum;
            }
        }
        return null;
    }

}
