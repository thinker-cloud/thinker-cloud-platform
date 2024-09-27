package com.thinker.cloud.upms.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysUserRoleDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysUserRoleVO;
import com.thinker.cloud.upms.model.entity.SysUserRole;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户角色 对象转换器
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserRoleConverter {

    SysUserRoleConverter INSTANTS = Mappers.getMapper(SysUserRoleConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysUserRole
     */
    SysUserRole toEntity(SysUserRoleDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysUserRoleVO
     */
    SysUserRoleVO toVO(SysUserRole entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysUserRole
     */
    SysUserRoleDTO toDTO(SysUserRole entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysUserRole>
     */
    List<SysUserRole> listToEntity(List<SysUserRoleDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysUserRoleVO>
     */
    List<SysUserRoleVO> listToVO(List<SysUserRole> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysUserRole>
     */
    List<SysUserRoleDTO> listToDTO(List<SysUserRole> list);
}
