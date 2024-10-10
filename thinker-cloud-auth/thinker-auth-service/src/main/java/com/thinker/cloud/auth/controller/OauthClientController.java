package com.thinker.cloud.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.auth.api.model.dto.OauthClientDTO;
import com.thinker.cloud.auth.api.model.query.OauthClientQuery;
import com.thinker.cloud.auth.api.model.vo.OauthClientVO;
import com.thinker.cloud.auth.service.IOauthClientService;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * oauth2客户端配置
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Tag(name = "OauthClientController", description = "oauth2客户端配置")
@RestController
@AllArgsConstructor
@RequestMapping("oauth/client")
public class OauthClientController {

    private final IOauthClientService oauthClientService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<OauthClientVO>> page(@RequestBody OauthClientQuery query) {
        IPage<OauthClientVO> page = query.generatePage();
        oauthClientService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<OauthClientVO>> list(@RequestBody OauthClientQuery query) {
        return Result.success(oauthClientService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<OauthClientVO> detail(@PathVariable Long id) {
        return Result.success(oauthClientService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid OauthClientDTO dto) {
        return Result.success(oauthClientService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid OauthClientDTO dto) {
        return Result.success(oauthClientService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(oauthClientService.removeById(id));
    }
}
