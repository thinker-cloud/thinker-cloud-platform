package com.thinker.cloud.upms.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import com.thinker.cloud.upms.api.sys.model.dto.SysParamsDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysParamsQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysParamsVO;
import com.thinker.cloud.upms.sys.service.ISysParamsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 公共参数
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Tag(name = "SysParamsController", description = "公共参数")
@RestController
@AllArgsConstructor
@RequestMapping("sys/params")
public class SysParamsController {

    private final ISysParamsService sysParamsService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysParamsVO>> page(@RequestBody SysParamsQuery query) {
        IPage<SysParamsVO> page = query.generatePage();
        sysParamsService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysParamsVO>> list(@RequestBody SysParamsQuery query) {
        return Result.success(sysParamsService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysParamsVO> detail(@PathVariable Long id) {
        return Result.success(sysParamsService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysParamsDTO dto) {
        return Result.success(sysParamsService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysParamsDTO dto) {
        return Result.success(sysParamsService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysParamsService.removeById(id));
    }
}
