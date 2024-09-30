package com.thinker.cloud.upms.sys.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysRoleDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysRoleVO;
import com.thinker.cloud.upms.sys.model.entity.SysRole;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 角色管理 对象转换器
 *
 * @author admin
 * @since 2024-09-23 11:39:09
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleConverter {

    SysRoleConverter INSTANTS = Mappers.getMapper(SysRoleConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysRole
     */
    SysRole toEntity(SysRoleDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysRoleVO
     */
    SysRoleVO toVO(SysRole entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysRole
     */
    SysRoleDTO toDTO(SysRole entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysRole>
     */
    List<SysRole> listToEntity(List<SysRoleDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysRoleVO>
     */
    List<SysRoleVO> listToVO(List<SysRole> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysRole>
     */
    List<SysRoleDTO> listToDTO(List<SysRole> list);
}
