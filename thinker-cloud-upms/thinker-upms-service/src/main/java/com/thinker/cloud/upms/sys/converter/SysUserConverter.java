package com.thinker.cloud.upms.sys.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysUserDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysUserVO;
import com.thinker.cloud.upms.api.uac.model.AuthUserDetail;
import com.thinker.cloud.upms.sys.model.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户信息 对象转换器
 *
 * @author admin
 * @since 2024-09-20 17:25:48
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserConverter {

    SysUserConverter INSTANTS = Mappers.getMapper(SysUserConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysUser
     */
    SysUser toEntity(SysUserDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysUserVO
     */
    SysUserVO toVO(SysUser entity);

    /**
     * entity to AuthUser
     *
     * @param sysUser sysUser
     * @return AuthUser
     */
    AuthUserDetail toAuth(SysUser sysUser);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysUser
     */
    SysUserDTO toDTO(SysUser entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysUser>
     */
    List<SysUser> listToEntity(List<SysUserDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysUserVO>
     */
    List<SysUserVO> listToVO(List<SysUser> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysUser>
     */
    List<SysUserDTO> listToDTO(List<SysUser> list);
}
