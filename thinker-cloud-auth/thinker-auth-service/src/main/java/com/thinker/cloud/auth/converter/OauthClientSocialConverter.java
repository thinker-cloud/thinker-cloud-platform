package com.thinker.cloud.auth.converter;

import com.thinker.cloud.auth.api.model.dto.OauthClientSocialDTO;
import com.thinker.cloud.auth.api.model.vo.OauthClientSocialVO;
import com.thinker.cloud.auth.model.entity.OauthClientSocial;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * oauth2客户端社交账号配置 对象转换器
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 **/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OauthClientSocialConverter {

    OauthClientSocialConverter INSTANTS = Mappers.getMapper(OauthClientSocialConverter.class);

    /**
     * dto to Entity
     *
     * @param dto dto
     * @return OauthClientSocial
     */
    OauthClientSocial toEntity(OauthClientSocialDTO dto);

    /**
     * entity to VO
     *
     * @param entity entity
     * @return OauthClientSocialVO
     */
    OauthClientSocialVO toVO(OauthClientSocial entity);

    /**
     * entity to DTO
     *
     * @param entity entity
     * @return OauthClientSocial
     */
    OauthClientSocialDTO toDTO(OauthClientSocial entity);

    /**
     * list DTO to entity
     *
     * @param list list
     * @return List<OauthClientSocial>
     */
    List<OauthClientSocial> listToEntity(List<OauthClientSocialDTO> list);

    /**
     * list entity to VO
     *
     * @param list list
     * @return List<OauthClientSocialVO>
     */
    List<OauthClientSocialVO> listToVO(List<OauthClientSocial> list);

    /**
     * list entity to DTO
     *
     * @param list list
     * @return List<OauthClientSocial>
     */
    List<OauthClientSocialDTO> listToDTO(List<OauthClientSocial> list);
}
