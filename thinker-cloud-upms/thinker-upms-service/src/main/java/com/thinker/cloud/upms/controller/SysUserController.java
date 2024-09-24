package com.thinker.cloud.upms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import com.thinker.cloud.upms.api.sys.model.dto.SysUserDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysUserQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysUserVO;
import com.thinker.cloud.upms.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户信息
 *
 * @author admin
 * @since 2024-09-20 17:25:48
 */
@Tag(name = "SysUserController", description = "用户信息")
@RestController
@AllArgsConstructor
@RequestMapping("sys-user")
public class SysUserController {

    private final ISysUserService sysUserService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysUserVO>> page(@RequestBody SysUserQuery query) {
        IPage<SysUserVO> page = query.generatePage();
        sysUserService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysUserVO>> list(@RequestBody SysUserQuery query) {
        return Result.success(sysUserService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysUserVO> detail(@PathVariable Long id) {
        return Result.success(sysUserService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysUserDTO dto) {
        return Result.success(sysUserService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysUserDTO dto) {
        return Result.success(sysUserService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysUserService.removeById(id));
    }
}
