package com.thinker.cloud.upms.sys.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysParamsBizDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysParamsBizVO;
import com.thinker.cloud.upms.sys.model.entity.SysParamsBiz;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 业务参数 对象转换器
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysParamsBizConverter {

    SysParamsBizConverter INSTANTS = Mappers.getMapper(SysParamsBizConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysParamsBiz
     */
    SysParamsBiz toEntity(SysParamsBizDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysParamsBizVO
     */
    SysParamsBizVO toVO(SysParamsBiz entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysParamsBiz
     */
    SysParamsBizDTO toDTO(SysParamsBiz entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysParamsBiz>
     */
    List<SysParamsBiz> listToEntity(List<SysParamsBizDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysParamsBizVO>
     */
    List<SysParamsBizVO> listToVO(List<SysParamsBiz> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysParamsBiz>
     */
    List<SysParamsBizDTO> listToDTO(List<SysParamsBiz> list);
}
