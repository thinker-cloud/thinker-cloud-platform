package com.thinker.cloud.upms.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysDictBizDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysDictBizVO;
import com.thinker.cloud.upms.model.entity.SysDictBiz;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 业务字典 对象转换器
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDictBizConverter {

    SysDictBizConverter INSTANTS = Mappers.getMapper(SysDictBizConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysDictBiz
     */
    SysDictBiz toEntity(SysDictBizDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysDictBizVO
     */
    SysDictBizVO toVO(SysDictBiz entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysDictBiz
     */
    SysDictBizDTO toDTO(SysDictBiz entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysDictBiz>
     */
    List<SysDictBiz> listToEntity(List<SysDictBizDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysDictBizVO>
     */
    List<SysDictBizVO> listToVO(List<SysDictBiz> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysDictBiz>
     */
    List<SysDictBizDTO> listToDTO(List<SysDictBiz> list);
}
