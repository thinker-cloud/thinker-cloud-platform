package com.thinker.cloud.upms.sys.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysRoleMenuDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysRoleMenuVO;
import com.thinker.cloud.upms.sys.model.entity.SysRoleMenu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 角色菜单 对象转换器
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleMenuConverter {

    SysRoleMenuConverter INSTANTS = Mappers.getMapper(SysRoleMenuConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysRoleMenu
     */
    SysRoleMenu toEntity(SysRoleMenuDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysRoleMenuVO
     */
    SysRoleMenuVO toVO(SysRoleMenu entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysRoleMenu
     */
    SysRoleMenuDTO toDTO(SysRoleMenu entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysRoleMenu>
     */
    List<SysRoleMenu> listToEntity(List<SysRoleMenuDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysRoleMenuVO>
     */
    List<SysRoleMenuVO> listToVO(List<SysRoleMenu> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysRoleMenu>
     */
    List<SysRoleMenuDTO> listToDTO(List<SysRoleMenu> list);
}
