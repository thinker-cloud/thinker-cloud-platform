package com.thinker.cloud.upms.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysOrganizationDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysOrganizationVO;
import com.thinker.cloud.upms.model.entity.SysOrganization;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 组织架构 对象转换器
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysOrganizationConverter {

    SysOrganizationConverter INSTANTS = Mappers.getMapper(SysOrganizationConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysOrganization
     */
    SysOrganization toEntity(SysOrganizationDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysOrganizationVO
     */
    SysOrganizationVO toVO(SysOrganization entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysOrganization
     */
    SysOrganizationDTO toDTO(SysOrganization entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysOrganization>
     */
    List<SysOrganization> listToEntity(List<SysOrganizationDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysOrganizationVO>
     */
    List<SysOrganizationVO> listToVO(List<SysOrganization> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysOrganization>
     */
    List<SysOrganizationDTO> listToDTO(List<SysOrganization> list);
}
