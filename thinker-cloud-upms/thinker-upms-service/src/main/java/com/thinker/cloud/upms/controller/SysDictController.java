package com.thinker.cloud.upms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import com.thinker.cloud.upms.api.sys.model.dto.SysDictDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysDictQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysDictVO;
import com.thinker.cloud.upms.service.ISysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 公共字典
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Tag(name = "SysDictController", description = "公共字典")
@RestController
@AllArgsConstructor
@RequestMapping("sys/dict")
public class SysDictController {

    private final ISysDictService sysDictService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysDictVO>> page(@RequestBody SysDictQuery query) {
        IPage<SysDictVO> page = query.generatePage();
        sysDictService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysDictVO>> list(@RequestBody SysDictQuery query) {
        return Result.success(sysDictService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysDictVO> detail(@PathVariable Long id) {
        return Result.success(sysDictService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysDictDTO dto) {
        return Result.success(sysDictService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysDictDTO dto) {
        return Result.success(sysDictService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysDictService.removeById(id));
    }
}
