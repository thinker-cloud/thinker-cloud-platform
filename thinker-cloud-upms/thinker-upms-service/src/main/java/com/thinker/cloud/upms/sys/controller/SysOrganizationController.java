package com.thinker.cloud.upms.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import com.thinker.cloud.upms.api.sys.model.dto.SysOrganizationDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysOrganizationQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysOrganizationVO;
import com.thinker.cloud.upms.sys.service.ISysOrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织架构
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Tag(name = "SysOrganizationController", description = "组织架构")
@RestController
@AllArgsConstructor
@RequestMapping("sys/organization")
public class SysOrganizationController {

    private final ISysOrganizationService sysOrganizationService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysOrganizationVO>> page(@RequestBody SysOrganizationQuery query) {
        IPage<SysOrganizationVO> page = query.generatePage();
        sysOrganizationService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysOrganizationVO>> list(@RequestBody SysOrganizationQuery query) {
        return Result.success(sysOrganizationService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysOrganizationVO> detail(@PathVariable Long id) {
        return Result.success(sysOrganizationService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysOrganizationDTO dto) {
        return Result.success(sysOrganizationService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysOrganizationDTO dto) {
        return Result.success(sysOrganizationService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysOrganizationService.removeById(id));
    }
}
