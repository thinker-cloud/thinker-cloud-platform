package com.thinker.cloud.upms.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysTenantDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysTenantVO;
import com.thinker.cloud.upms.model.entity.SysTenant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户管理 对象转换器
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysTenantConverter {

    SysTenantConverter INSTANTS = Mappers.getMapper(SysTenantConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysTenant
     */
    SysTenant toEntity(SysTenantDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysTenantVO
     */
    SysTenantVO toVO(SysTenant entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysTenant
     */
    SysTenantDTO toDTO(SysTenant entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysTenant>
     */
    List<SysTenant> listToEntity(List<SysTenantDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysTenantVO>
     */
    List<SysTenantVO> listToVO(List<SysTenant> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysTenant>
     */
    List<SysTenantDTO> listToDTO(List<SysTenant> list);
}
