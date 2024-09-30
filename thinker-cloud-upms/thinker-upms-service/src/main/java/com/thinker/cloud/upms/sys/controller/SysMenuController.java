package com.thinker.cloud.upms.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import com.thinker.cloud.upms.api.sys.model.dto.SysMenuDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysMenuQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysMenuVO;
import com.thinker.cloud.upms.sys.service.ISysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单管理
 *
 * @author admin
 * @since 2024-09-27 17:43:02
 */
@Tag(name = "SysMenuController", description = "菜单管理")
@RestController
@AllArgsConstructor
@RequestMapping("sys/menu")
public class SysMenuController {

    private final ISysMenuService sysMenuService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysMenuVO>> page(@RequestBody SysMenuQuery query) {
        IPage<SysMenuVO> page = query.generatePage();
        sysMenuService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysMenuVO>> list(@RequestBody SysMenuQuery query) {
        return Result.success(sysMenuService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysMenuVO> detail(@PathVariable Long id) {
        return Result.success(sysMenuService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysMenuDTO dto) {
        return Result.success(sysMenuService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysMenuDTO dto) {
        return Result.success(sysMenuService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysMenuService.removeById(id));
    }
}
