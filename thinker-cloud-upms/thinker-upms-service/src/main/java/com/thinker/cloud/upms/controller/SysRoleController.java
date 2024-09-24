package com.thinker.cloud.upms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.thinker.cloud.upms.service.ISysRoleService;
import com.thinker.cloud.upms.model.dto.SysRoleDTO;
import com.thinker.cloud.upms.model.query.SysRoleQuery;
import com.thinker.cloud.upms.model.vo.SysRoleVO;

import org.springframework.web.bind.annotation.RestController;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色管理
 *
 * @author admin
 * @since 2024-09-23 11:39:09
 */
@Tag(name = "SysRoleController", description = "角色管理")
@RestController
@AllArgsConstructor
@RequestMapping("sys-role")
public class SysRoleController {

    private final ISysRoleService sysRoleService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysRoleVO>> page(@RequestBody SysRoleQuery query) {
        IPage<SysRoleVO> page = query.generatePage();
        sysRoleService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysRoleVO>> list(@RequestBody SysRoleQuery query) {
        return Result.success(sysRoleService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysRoleVO> detail(@PathVariable Long id) {
        return Result.success(sysRoleService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysRoleDTO dto) {
        return Result.success(sysRoleService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysRoleDTO dto) {
        return Result.success(sysRoleService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysRoleService.removeById(id));
    }
}
