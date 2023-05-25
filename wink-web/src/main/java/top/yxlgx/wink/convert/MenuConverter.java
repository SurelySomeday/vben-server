package top.yxlgx.wink.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.yxlgx.wink.entity.Menu;
import top.yxlgx.wink.entity.dto.MenuDTO;

/**
 * @author yanxin
 * @Description:
 */
@Mapper
public interface MenuConverter {
    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

    Menu convert(MenuDTO menu);
}

