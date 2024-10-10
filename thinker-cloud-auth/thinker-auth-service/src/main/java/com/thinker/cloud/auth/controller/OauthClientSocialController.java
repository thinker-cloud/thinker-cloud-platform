package com.thinker.cloud.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.auth.api.model.dto.OauthClientSocialDTO;
import com.thinker.cloud.auth.api.model.query.OauthClientSocialQuery;
import com.thinker.cloud.auth.api.model.vo.OauthClientSocialVO;
import com.thinker.cloud.auth.service.IOauthClientSocialService;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * oauth2客户端社交账号配置
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Tag(name = "OauthClientSocialController", description = "oauth2客户端社交账号配置")
@RestController
@AllArgsConstructor
@RequestMapping("oauth/client/social")
public class OauthClientSocialController {

    private final IOauthClientSocialService oauthClientSocialService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<OauthClientSocialVO>> page(@RequestBody OauthClientSocialQuery query) {
        IPage<OauthClientSocialVO> page = query.generatePage();
        oauthClientSocialService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<OauthClientSocialVO>> list(@RequestBody OauthClientSocialQuery query) {
        return Result.success(oauthClientSocialService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<OauthClientSocialVO> detail(@PathVariable Long id) {
        return Result.success(oauthClientSocialService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid OauthClientSocialDTO dto) {
        return Result.success(oauthClientSocialService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid OauthClientSocialDTO dto) {
        return Result.success(oauthClientSocialService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(oauthClientSocialService.removeById(id));
    }
}
