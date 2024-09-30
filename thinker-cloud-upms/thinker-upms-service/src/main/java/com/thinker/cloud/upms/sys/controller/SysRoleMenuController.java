package com.thinker.cloud.upms.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import com.thinker.cloud.upms.api.sys.model.dto.SysRoleMenuDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysRoleMenuQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysRoleMenuVO;
import com.thinker.cloud.upms.sys.service.ISysRoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色菜单
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Tag(name = "SysRoleMenuController", description = "角色菜单")
@RestController
@AllArgsConstructor
@RequestMapping("sys/role/menu")
public class SysRoleMenuController {

    private final ISysRoleMenuService sysRoleMenuService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysRoleMenuVO>> page(@RequestBody SysRoleMenuQuery query) {
        IPage<SysRoleMenuVO> page = query.generatePage();
        sysRoleMenuService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysRoleMenuVO>> list(@RequestBody SysRoleMenuQuery query) {
        return Result.success(sysRoleMenuService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysRoleMenuVO> detail(@PathVariable Long id) {
        return Result.success(sysRoleMenuService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysRoleMenuDTO dto) {
        return Result.success(sysRoleMenuService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysRoleMenuDTO dto) {
        return Result.success(sysRoleMenuService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysRoleMenuService.removeById(id));
    }
}
