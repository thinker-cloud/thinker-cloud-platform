package com.thinker.cloud.upms.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import com.thinker.cloud.upms.api.sys.model.dto.SysParamsBizDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysParamsBizQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysParamsBizVO;
import com.thinker.cloud.upms.sys.service.ISysParamsBizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 业务参数
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Tag(name = "SysParamsBizController", description = "业务参数")
@RestController
@AllArgsConstructor
@RequestMapping("sys/params/biz")
public class SysParamsBizController {

    private final ISysParamsBizService sysParamsBizService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysParamsBizVO>> page(@RequestBody SysParamsBizQuery query) {
        IPage<SysParamsBizVO> page = query.generatePage();
        sysParamsBizService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysParamsBizVO>> list(@RequestBody SysParamsBizQuery query) {
        return Result.success(sysParamsBizService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysParamsBizVO> detail(@PathVariable Long id) {
        return Result.success(sysParamsBizService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysParamsBizDTO dto) {
        return Result.success(sysParamsBizService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysParamsBizDTO dto) {
        return Result.success(sysParamsBizService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysParamsBizService.removeById(id));
    }
}