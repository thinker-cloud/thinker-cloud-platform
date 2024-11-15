package com.thinker.cloud.auth.converter;

import com.thinker.cloud.auth.api.model.dto.OauthClientDTO;
import com.thinker.cloud.auth.api.model.vo.OauthClientVO;
import com.thinker.cloud.auth.model.entity.OauthClient;
import com.thinker.cloud.security.repository.entity.RedisRegisteredClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * oauth2客户端配置 对象转换器
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OauthClientConverter {

    OauthClientConverter INSTANTS = Mappers.getMapper(OauthClientConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return OauthClient
     */
    OauthClient toEntity(OauthClientDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return OauthClientVO
     */
    OauthClientVO toVO(OauthClient entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return OauthClient
     */
    OauthClientDTO toDTO(OauthClient entity);

    /**
     * entity to Repository
     *
     * @param entity entity
     * @return RedisRegisteredClient
     */
    @Mapping(target = "id", expression = "java(String.valueOf(entity.getId()))")
    RedisRegisteredClient toRepository(OauthClient entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<OauthClient>
     */
    List<OauthClient> listToEntity(List<OauthClientDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<OauthClientVO>
     */
    List<OauthClientVO> listToVO(List<OauthClient> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<OauthClient>
     */
    List<OauthClientDTO> listToDTO(List<OauthClient> list);

    /**
     * list entity to Repository
     *
     * @param list list
     * @return List<RedisRegisteredClient>
     */
    List<RedisRegisteredClient> listToRepository(List<OauthClient> list);
}
