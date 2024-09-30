package com.thinker.cloud.upms.sys.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysMenuDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysMenuVO;
import com.thinker.cloud.upms.sys.model.entity.SysMenu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 菜单管理 对象转换器
 *
 * @author admin
 * @since 2024-09-27 17:43:02
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMenuConverter {

    SysMenuConverter INSTANTS = Mappers.getMapper(SysMenuConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysMenu
     */
    SysMenu toEntity(SysMenuDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysMenuVO
     */
    SysMenuVO toVO(SysMenu entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysMenu
     */
    SysMenuDTO toDTO(SysMenu entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysMenu>
     */
    List<SysMenu> listToEntity(List<SysMenuDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysMenuVO>
     */
    List<SysMenuVO> listToVO(List<SysMenu> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysMenu>
     */
    List<SysMenuDTO> listToDTO(List<SysMenu> list);
}
