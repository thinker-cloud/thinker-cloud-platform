package com.thinker.cloud.upms.converter;

import com.thinker.cloud.upms.api.sys.model.dto.SysDictDTO;
import com.thinker.cloud.upms.api.sys.model.vo.SysDictVO;
import com.thinker.cloud.upms.model.entity.SysDict;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 公共字典 对象转换器
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDictConverter {

    SysDictConverter INSTANTS = Mappers.getMapper(SysDictConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return SysDict
     */
    SysDict toEntity(SysDictDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return SysDictVO
     */
    SysDictVO toVO(SysDict entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return SysDict
     */
    SysDictDTO toDTO(SysDict entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<SysDict>
     */
    List<SysDict> listToEntity(List<SysDictDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<SysDictVO>
     */
    List<SysDictVO> listToVO(List<SysDict> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<SysDict>
     */
    List<SysDictDTO> listToDTO(List<SysDict> list);
}
