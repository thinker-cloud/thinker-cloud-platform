package com.thinker.cloud.upms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import com.thinker.cloud.upms.api.sys.model.dto.SysTenantDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysTenantQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysTenantVO;
import com.thinker.cloud.upms.service.ISysTenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 租户管理
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Tag(name = "SysTenantController", description = "租户管理")
@RestController
@AllArgsConstructor
@RequestMapping("sys/tenant")
public class SysTenantController {

    private final ISysTenantService sysTenantService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysTenantVO>> page(@RequestBody SysTenantQuery query) {
        IPage<SysTenantVO> page = query.generatePage();
        sysTenantService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysTenantVO>> list(@RequestBody SysTenantQuery query) {
        return Result.success(sysTenantService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysTenantVO> detail(@PathVariable Long id) {
        return Result.success(sysTenantService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysTenantDTO dto) {
        return Result.success(sysTenantService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysTenantDTO dto) {
        return Result.success(sysTenantService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysTenantService.removeById(id));
    }
}
