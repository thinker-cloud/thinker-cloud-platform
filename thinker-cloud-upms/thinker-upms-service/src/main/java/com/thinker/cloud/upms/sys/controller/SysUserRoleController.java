package com.thinker.cloud.upms.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import com.thinker.cloud.upms.api.sys.model.dto.SysUserRoleDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysUserRoleQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysUserRoleVO;
import com.thinker.cloud.upms.sys.service.ISysUserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户角色
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Tag(name = "SysUserRoleController", description = "用户角色")
@RestController
@AllArgsConstructor
@RequestMapping("sys/user/role")
public class SysUserRoleController {

    private final ISysUserRoleService sysUserRoleService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysUserRoleVO>> page(@RequestBody SysUserRoleQuery query) {
        IPage<SysUserRoleVO> page = query.generatePage();
        sysUserRoleService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysUserRoleVO>> list(@RequestBody SysUserRoleQuery query) {
        return Result.success(sysUserRoleService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysUserRoleVO> detail(@PathVariable Long id) {
        return Result.success(sysUserRoleService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysUserRoleDTO dto) {
        return Result.success(sysUserRoleService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysUserRoleDTO dto) {
        return Result.success(sysUserRoleService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysUserRoleService.removeById(id));
    }
}
