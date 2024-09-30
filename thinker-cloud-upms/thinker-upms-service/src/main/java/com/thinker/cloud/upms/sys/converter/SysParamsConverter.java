package com.thinker.cloud.upms.sys.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysParamsDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysParamsVO;
import com.thinker.cloud.upms.sys.model.entity.SysParams;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 公共参数 对象转换器
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysParamsConverter {

    SysParamsConverter INSTANTS = Mappers.getMapper(SysParamsConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysParams
     */
    SysParams toEntity(SysParamsDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysParamsVO
     */
    SysParamsVO toVO(SysParams entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysParams
     */
    SysParamsDTO toDTO(SysParams entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysParams>
     */
    List<SysParams> listToEntity(List<SysParamsDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysParamsVO>
     */
    List<SysParamsVO> listToVO(List<SysParams> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysParams>
     */
    List<SysParamsDTO> listToDTO(List<SysParams> list);
}
